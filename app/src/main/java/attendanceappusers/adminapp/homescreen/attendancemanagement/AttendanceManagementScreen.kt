package attendanceappusers.adminapp.homescreen.attendancemanagement

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.adminapp.homescreen.attendancemanagement.updateattendance.AttendanceToUpdateConfirmationDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.CustomDatePicker
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    var query by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }

    val context = LocalContext.current

    val attendances by viewModel.attendances.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val sortedAttendances = attendances.sortedByDescending { attendance ->
        LocalDate.parse(attendance.date, formatter)
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    var attendanceToDelete by remember { mutableStateOf<Attendance?>(null) }
    var attendanceToUpdate by remember { mutableStateOf<Attendance?>(null) }
    var selectedStatus by remember { mutableStateOf(attendanceToUpdate?.status ?: "") }
    var result by remember { mutableStateOf(Results.UpdateAttendanceResult()) }

    var selectedSubjectCode by remember { mutableStateOf("All") }
    val subjects by viewModel.subjects.collectAsState()

    var selectedUserType by remember { mutableStateOf("All") }
    val userType = listOf("All", "Admin", "Student", "Faculty")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Attendance Management",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

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
            placeholder = { Text("Enter User ID or Name") },
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

        UniversalDropDownMenu(
            label = "Subject",
            items = subjects,
            selectedItem = selectedSubjectCode,
            onItemSelected = {
                selectedSubjectCode = it.split(" - ")[0]
            }
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedUserType,
                label = { Text("User Type") },
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                userType.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedUserType = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate(AdminHomeScreen.HomeScreen.name) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back to Home"
                    )

                    Text(
                        text = "Back to Home",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { navController.navigate(AdminHomeScreen.SearchStudent.name) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Attendance",
                        fontSize = 12.sp,
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
                            selectedStatus,
                            attendance.usertype
                        )
                    )
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

    LaunchedEffect(query, startDate, endDate, selectedSubjectCode, selectedUserType) {
        val startDateString = startDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) // Format start date
        val endDateString = endDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) // Format end date

        println("Query: $query")
        println("Start Date: $startDateString")
        println("End Date: $endDateString")
        println("Selected Subject Code: $selectedSubjectCode")
        println("Selected Status: $selectedUserType")

        viewModel.filterAttendancesByAdmin(
            searchQuery = query,
            subjectCode = selectedSubjectCode,
            usertype = selectedUserType,
            startDate = startDateString,
            endDate = endDateString
        )
    }
}