package attendanceappusers.adminapp.subject.adminsubject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.adminapp.homescreen.subjectmanagement.SubjectManagementViewModel
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UpdateSubject
import com.attendanceapp2.data.model.subject.UpdatingSubjectHolder
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.navigation.approutes.admin.AdminSubject
import kotlinx.coroutines.launch

@Composable
fun AdminSubjectScreen (
    navController: NavController,
    viewModel: AdminSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectManagement : SubjectManagementViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedSubject = SelectedSubjectHolder.getSelectedSubject()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showArchiveDialog by remember { mutableStateOf(false) }

    var subjectToDelete by remember { mutableStateOf<Subject?>(null) }
    var subjectToArchive by remember { mutableStateOf<Subject?>(null) }

    val subjectSchedules = viewModel.subjectSchedules.collectAsState()

    // Use LaunchedEffect to fetch subject schedules when the composable is first launched
    LaunchedEffect(selectedSubject) {
        selectedSubject?.let { viewModel.getSubjectSchedules(it.id) }
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
                            text = "Code/Name:",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${selectedSubject.code} - ${selectedSubject.name}",
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
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = selectedSubject.faculty,
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
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = selectedSubject.room,
                            modifier = Modifier.weight(3f)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Join Code:",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = selectedSubject.joinCode,
                            modifier = Modifier.weight(3f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        FloatingActionButton(
                            onClick = {
                                UpdatingSubjectHolder.setSelectedSubject(
                                    UpdateSubject(
                                        selectedSubject.id,
                                        selectedSubject.code,
                                        selectedSubject.name,
                                        selectedSubject.room,
                                        selectedSubject.faculty,
                                        selectedSubject.subjectStatus,
                                        selectedSubject.joinCode
                                    )
                                )
                                navController.navigate(AdminHomeScreen.UpdateSubject.name)
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(Icons.Default.Update, contentDescription = "Update")
                        }

                        FloatingActionButton(
                            onClick = {
                                subjectToDelete =
                                    Subject(
                                        selectedSubject.id,
                                        selectedSubject.code,
                                        selectedSubject.name,
                                        selectedSubject.room,
                                        selectedSubject.faculty,
                                        selectedSubject.subjectStatus,
                                        selectedSubject.joinCode
                                    )
                                showDeleteDialog = true
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }

                        FloatingActionButton(
                            onClick = {
                                subjectToArchive =
                                    Subject(
                                        selectedSubject.id,
                                        selectedSubject.code,
                                        selectedSubject.name,
                                        selectedSubject.room,
                                        selectedSubject.faculty,
                                        selectedSubject.subjectStatus,
                                        selectedSubject.joinCode
                                    )
                                showArchiveDialog = true
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(Icons.Filled.Archive, contentDescription = "Archive")
                        }
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Subject Schedules",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                // Display schedules if available
                if (subjectSchedules.value.isNotEmpty()) {
                    subjectSchedules.value.forEach { schedule ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${schedule.day}:",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${schedule.start} - ${schedule.end}",
                                modifier = Modifier.weight(3f)
                            )
                        }

                    }
                } else {
                    Text(text = "No schedules available")
                }

                // Add floating action button
                FloatingActionButton(
                    onClick = { navController.navigate(AdminSubject.AddSchedule.name) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
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
                onClick = { navController.navigateUp() },
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
                        contentDescription = "Back"
                    )

                    Text(
                        text = "Back",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }


            FloatingActionButton(
                onClick = { navController.navigate(AdminSubject.SubjectAttendance.name) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View Attendances",
                        fontSize = 16.sp,
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

    ConfirmDialog(
        title = "Delete Confirmation",
        message = "Are you sure you want to delete this subject?",
        onConfirm = {
            subjectToDelete?.let { subject ->
                subjectManagement.deleteSubject(subject)
                showDeleteDialog = false
                subjectToDelete = null
            }
            subjectManagement.updateSubjectLists()
            navController.navigate(AdminHomeScreen.SubjectManagement.name)
        },
        onDismiss = {
            showDeleteDialog = false
            subjectToDelete = null
        },
        showDialog = showDeleteDialog
    )

    ConfirmDialog(
        title = "Archive Confirmation",
        message = "Are you sure you want to archive this subject?",
        onConfirm = {
            subjectToArchive?.let { subject ->
                subjectManagement.archiveSubject(subject)
                showArchiveDialog = false
                subjectToArchive = null
            }
            subjectManagement.updateSubjectLists()
        },
        onDismiss = {
            showArchiveDialog = false
            subjectToArchive = null
        },
        showDialog = showArchiveDialog
    )
}