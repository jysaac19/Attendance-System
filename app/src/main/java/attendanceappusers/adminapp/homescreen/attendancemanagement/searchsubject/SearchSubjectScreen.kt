package attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.user.SelectedStudentHolder
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SearchSubjectScreen(
    navController: NavController,
    viewModel: SearchSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val subjects by viewModel.subjects.collectAsState()
    val selectedStudent = SelectedStudentHolder.getSelectedStudent()

    var showDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("Present") }
    var result by remember { mutableStateOf(Results.AddAttendanceResult()) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Select Subject",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
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
            placeholder = { Text("Enter Subject ID or Subject Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Back to Home",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back to Home"
                    )

                }
            }
        }

        if (selectedStudent != null) {
            Card(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Selected Student",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(text = "Name:", fontSize = 12.sp)
                            Text(text = "Email:", fontSize = 12.sp)
                            Text(text = "Department:", fontSize = 12.sp)
                        }

                        Column (
                            modifier = Modifier.weight(2f),
                        ) {
                            Text(text = "${selectedStudent.firstname} ${selectedStudent.lastname}", fontSize = 10.sp)
                            Text(text = selectedStudent.email, fontSize = 10.sp)
                            Text(text = selectedStudent.department, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        LazyColumn {
            items(subjects) { subject ->
                SubjectListItems(
                    subject = subject,
                    onClick = {
                        coroutineScope.launch {
                            SelectedSubjectHolder.setSelectedSubject(
                                SelectedSubject(
                                    subject.id,
                                    subject.code,
                                    subject.name,
                                    subject.room,
                                    subject.facultyName,
                                    subject.subjectStatus,
                                    subject.joinCode
                                )
                            )
                            showDialog = true
                        }
                    }
                )
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
        val query = searchText.text.trim()
        viewModel.searchSubjects(query)
    }
}