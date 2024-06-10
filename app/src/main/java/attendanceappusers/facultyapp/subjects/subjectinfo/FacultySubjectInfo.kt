package attendanceappusers.facultyapp.subjects.subjectinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.studentapp.subjects.subjectinfo.StudentSubjectInfoViewModel
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.navigation.approutes.faculty.FacultyMainRoute
import com.attendanceapp2.navigation.approutes.faculty.FacultySubjectsRoutes
import com.attendanceapp2.navigation.approutes.student.StudentMainRoute
import com.attendanceapp2.navigation.approutes.student.StudentSubjectsRoutes

@Composable
fun FacultySubjectInfo(
    navController: NavController,
    viewModel: StudentSubjectInfoViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val subjectSchedules = viewModel.subjectSchedules.collectAsState()
    val selectedSubject = SelectedSubjectHolder.getSelectedSubject()

    LaunchedEffect(selectedSubject) {
        selectedSubject?.let { viewModel.updateOfflineSchedules(it.id) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Subject Information",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                if (selectedSubject != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Code - Name:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = "${selectedSubject.code} - ${selectedSubject.name}",
                            fontSize = 10.sp,
                            modifier = Modifier.weight(3f)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Faculty:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = selectedSubject.faculty,
                            fontSize = 10.sp,
                            modifier = Modifier.weight(3f)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Room:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = selectedSubject.room,
                            fontSize = 10.sp,
                            modifier = Modifier.weight(3f)
                        )
                    }
                } else {
                    Text(text = "No subject selected")
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Subject Schedules",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                if (subjectSchedules.value.isNotEmpty()) {
                    subjectSchedules.value.forEach { schedule ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ) {
                            Text(
                                text = "${schedule.day}:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1.5f)
                            )
                            Text(
                                text = "${schedule.start} - ${schedule.end}",
                                fontSize = 12.sp,
                                modifier = Modifier.weight(3f)
                            )
                        }
                    }
                } else {
                    Text(text = "No schedules available", fontSize = 12.sp)
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
            FloatingActionButton(
                onClick = { navController.navigate(FacultyMainRoute.Subjects.name) },
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
                        text = "Back",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }


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
                    Text(
                        text = "View Attendances",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Go To Subject Attendances"
                    )
                }
            }
        }
    }
}