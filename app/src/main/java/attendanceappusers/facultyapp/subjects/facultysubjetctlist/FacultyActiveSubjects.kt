package attendanceappusers.facultyapp.subjects.facultysubjetctlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.facultyapp.subjects.facultysubjectattendances.FacultySubjectAttendanceViewModel
import attendanceappusers.adminapp.notification.NotificationViewModel
import attendanceappusers.studentapp.subjects.joinsubject.JoinSubjectDialog
import attendanceappusers.studentapp.subjects.joinsubject.JoinSubjectViewModel
import com.attendanceapp2.navigation.approutes.faculty.FacultySubjectsRoutes
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.screenuniversalcomponents.subjectuicomponents.SubjectCard
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Notifications
import com.attendanceapp2.data.model.showToast
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Month

@Composable
fun FacultyActiveSubjects (
    navController : NavController,
    viewModel: SubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    facultySubjectAttendanceVM: FacultySubjectAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory),
    joinViewModel: JoinSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    notificationViewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val loggedInUser = LoggedInUserHolder.getLoggedInUser()

    val subjects by viewModel.activeSubjects.collectAsState()
    val currentMonth = Month.entries[java.time.LocalDate.now().monthValue - 1]
    val schoolYearText = when (currentMonth) {
        in Month.SEPTEMBER..Month.DECEMBER -> {
            "S.Y. ${java.time.LocalDate.now().year} - ${java.time.LocalDate.now().year + 1}"
        }
        in Month.JANUARY..Month.JUNE -> {
            "S.Y. ${java.time.LocalDate.now().year - 1} - ${java.time.LocalDate.now().year}"
        }
        else -> {
            "Summer Class"
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.fetchActiveSubjectsForLoggedInUser()
        viewModel.fetchArchivedSubjectsForLoggedInUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Subjects",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            schoolYearText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Join Subject"
                    )

                    Text(
                        text = "Join Subject",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate(FacultySubjectsRoutes.FacultyArchivedSubjects.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Archive,
                        contentDescription = "Archived Subjects"
                    )

                    Text(
                        text = "Archived Subjects",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(subjects) { subject ->
                SubjectCard(subject = subject) {
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
                    Timber.d("Selected subject: ${SelectedSubjectHolder.getSelectedSubject()}")
                    navController.navigate(FacultySubjectsRoutes.FacultySubjectInfo.name)
                }
            }
        }

        JoinSubjectDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onJoinSubject = { subjectCode ->
                coroutineScope.launch {
                    loggedInUser?.let { user ->
                        val joinResult = joinViewModel.joinSubjectByCode(user.id, subjectCode)
                        joinResult.successMessage?.let {
                            showDialog = false
                            showToast(context, it)
                            viewModel.fetchActiveSubjectsForLoggedInUser()
                            notificationViewModel.insertNotifications(
                                Notifications(
                                    title = "You have joined to $subjectCode",
                                    message = "Your joining code have been accepted. Please proceed to your class subject",
                                    portal = "student"
                                ))
                        }
                        joinResult.failureMessage?.let {
                            showToast(context, it)
                        }
                    }
                }
            }
        )
    }
}