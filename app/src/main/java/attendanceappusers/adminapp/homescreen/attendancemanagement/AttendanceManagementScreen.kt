package attendanceappusers.adminapp.homescreen.attendancemanagement

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.AttendanceStatusConfirmationDialog
import attendanceappusers.adminapp.homescreen.attendancemanagement.updateattendance.AttendanceToUpdateConfirmationDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.SelectedStudentHolder.selectedStudent
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.CustomDatePicker
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceManagementScreen (
    navController: NavController,
    viewModel: AttendanceManagementViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Get the current year
    val currentYear = LocalDate.now().year
    val coroutineScope = rememberCoroutineScope()

    // Default start date and end date
    val defaultEndDate = LocalDate.now()
    val defaultStartDate = LocalDate.of(currentYear, 1, 1) // January 1st of the current year

    var query by remember { mutableStateOf(TextFieldValue()) }
    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }

    val context = LocalContext.current

    val attendances by viewModel.attendances.collectAsState()
    val sortedAttendances = attendances.sortedByDescending { attendance ->
        LocalDate.parse(attendance.date, DateTimeFormatter.ofPattern("MM-dd-yyyy"))
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    var attendanceToDelete by remember { mutableStateOf<Attendance?>(null) }
    var attendanceToUpdate by remember { mutableStateOf<Attendance?>(null) }
    var selectedStatus by remember { mutableStateOf(attendanceToUpdate?.status ?: "") }


    var result by remember { mutableStateOf(Results.UpdateAttendanceResult()) }

    LaunchedEffect(true){
        val startDateString = startDate.toString()
        val endDateString = endDate.toString()
        viewModel.filterAttendancesByAdmin(
            query.text.trim(),
            startDateString,
            endDateString
        )
    }

    LaunchedEffect(query, startDate, endDate) {
        val startDateString = startDate.toString()
        val endDateString = endDate.toString()
        viewModel.filterAttendancesByAdmin(
            query.text.trim(),
            startDateString,
            endDateString
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Attendance Management",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
            },
            label = { Text("Search") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { /* TODO: Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter User ID or Full Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CustomDatePicker(
                    label = "From",
                    selectedDate = startDate,
                    onDateSelected = { date ->
                        startDate = date
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                CustomDatePicker(
                    label = "To",
                    selectedDate = endDate,
                    onDateSelected = { date ->
                        endDate = date
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(AdminHomeScreen.HomeScreen.name) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back to Home"
                    )

                    Text(
                        text = "Back to Home",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate(AdminHomeScreen.SearchStudent.name) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Attendance",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Attendance"
                    )
                }
            }
        }

        // LazyColumn to display attendances
        LazyColumn {
            items(sortedAttendances) { attendance ->
                AttendanceCard(
                    attendance = attendance,
                    onDelete = {
                        attendanceToDelete = attendance
                        showDeleteDialog = true
                    },
                    onUpdate = {
                        attendanceToUpdate = attendance
                        showUpdateDialog = true
                    }
                )
            }
        }
    }

    ConfirmDialog(
        title = "Delete Confirmation",
        message = "Are you sure you want to delete this attendance?",
        onConfirm = {
            attendanceToDelete?.let { attendance ->
                viewModel.deleteAttendance(attendance)
                showDeleteDialog = false
                attendanceToDelete = null
                viewModel.fetchAttendances()
            }
        },
        onDismiss = {
            showDeleteDialog = false
            attendanceToDelete = null
        },
        showDialog = showDeleteDialog
    )

    AttendanceToUpdateConfirmationDialog(
        student = attendanceToUpdate,
        title = "Confirm Attendance",
        text = "Are you sure you want to add attendance for ${attendanceToUpdate?.firstname} ${attendanceToUpdate?.lastname}?",
        onConfirm = {
            coroutineScope.launch {
                attendanceToUpdate?.let { attendance ->
                    viewModel.updateAttendance(
                        Attendance(
                            attendance.id,
                            attendance.userId,
                            attendance.firstname,
                            attendance.lastname,
                            attendance.subjectId,
                            attendance.subjectName,
                            attendance.subjectCode,
                            attendance.date,
                            attendance.time,
                            selectedStatus.toString()
                        )
                    )
                    viewModel.fetchAttendances()
                    showUpdateDialog = false
                    attendanceToUpdate = null
                }
            }
        },
        onDismiss = {
            showUpdateDialog = false
        },
        selectedStatus = selectedStatus.toString(),
        onStatusSelected = { newStatus -> selectedStatus = newStatus },
        showDialog = showUpdateDialog
    )
}