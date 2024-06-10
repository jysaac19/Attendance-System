package attendanceappusers.facultyapp.subjects.searchstudent

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent.StudentListItems
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.AttendanceStatusConfirmationDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.user.SelectedStudent
import com.attendanceapp2.data.model.user.SelectedStudentHolder
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.navigation.approutes.faculty.FacultySubjectsRoutes
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultySearchStudentScreen(
    navController: NavController,
    viewModel: FacultySearchStudentViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    val students by viewModel.students.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("Present") }
    var result by remember { mutableStateOf(Results.AddAttendanceResult()) }
    val selectedStudent = SelectedStudentHolder.getSelectedStudent()

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Select Student",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it.uppercase()
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
            placeholder = { Text("Enter User ID or Student Name") }
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(FacultySubjectsRoutes.FacultySubjectAttendances.name) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )

                    Text(
                        text = "Back to Subject Attendances",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        LazyColumn {
            items(students) { student ->
                StudentListItems(student) {
                    SelectedStudentHolder.setSelectedStudent(
                        SelectedStudent(
                            student.id,
                            student.firstname,
                            student.lastname,
                            student.email,
                            student.password,
                            student.usertype,
                            student.department,
                            student.status
                        )
                    )
                    showDialog = true
                }
            }
        }
    }

    AttendanceStatusConfirmationDialog(
        student = selectedStudent,
        title = "Confirm Attendance",
        text = "Are you sure you want to add attendance for ${selectedStudent?.firstname} ${selectedStudent?.lastname}?",
        onConfirm = {
            coroutineScope.launch {
                // Call insertAttendance with selected data and selectedStatus
                selectedStudent?.let { student ->
                    val subject = SelectedSubjectHolder.getSelectedSubject()
                    if (subject != null) {
                        val currentDate =
                            LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                        val currentTime =
                            LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))
                        result = viewModel.insertAttendance(
                            Attendance(
                                userId = student.id,
                                firstname = student.firstname,
                                lastname = student.lastname,
                                subjectId = subject.id,
                                subjectName = subject.name,
                                subjectCode = subject.code,
                                date = currentDate,
                                time = currentTime,
                                status = selectedStatus,
                                usertype = student.usertype,
                            )
                        )
                        showDialog = false
                    }
                }
            }
        },
        onDismiss = {
            showDialog = false
        },
        selectedStatus = selectedStatus,
        onStatusSelected = { newStatus -> selectedStatus = newStatus },
        showDialog = showDialog
        // Pass selectedStatus to AttendanceStatusConfirmationDialog
        // Pass setter function
    )

    LaunchedEffect(result) {
        // Show a toast for success message
        result.successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        // Show a toast for failure message
        result.failureMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        // Reset the result to avoid showing the same toast again
        result = Results.AddAttendanceResult()
    }

    LaunchedEffect(searchText) {
        viewModel.searchStudents(searchText)
    }
}