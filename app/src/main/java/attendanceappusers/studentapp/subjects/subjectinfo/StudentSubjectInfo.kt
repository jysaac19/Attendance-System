package attendanceappusers.studentapp.subjects.subjectinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.navigation.approutes.student.StudentMainRoute
import com.attendanceapp2.navigation.approutes.student.StudentSubjectsRoutes

@Composable
fun StudentSubjectInfo(
    navController: NavController,
    viewModel: StudentSubjectInfoViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val subjectSchedules = viewModel.subjectSchedules.collectAsState()
    val selectedSubject = SelectedSubjectHolder.getSelectedSubject()

    LaunchedEffect(selectedSubject) {
        selectedSubject?.let { viewModel.updateOfflineSchedules(it.id) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .fillMaxWidth()
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
                            modifier = Modifier
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = "Code - Name:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Faculty:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Room:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            Column(
                                modifier = Modifier.weight(2f),
                            ) {
                                Text(
                                    text = "${selectedSubject.code} - ${selectedSubject.name}",
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = selectedSubject.faculty,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = selectedSubject.room,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    } else {
                        Text(text = "No subject selected")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .fillMaxWidth()
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column (
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Text(
                                        text = "${schedule.day}:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Column (
                                    modifier = Modifier.weight(2f),
                                ) {
                                    Text(
                                        text = "${schedule.start} - ${schedule.end}",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    } else {
                        Text(text = "No schedules available", fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(StudentMainRoute.Subjects.name) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .height(50.dp)
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
                    onClick = { navController.navigate(StudentSubjectsRoutes.StudentSubjectAttendances.name) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .height(50.dp)
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
}