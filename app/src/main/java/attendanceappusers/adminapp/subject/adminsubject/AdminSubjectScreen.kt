package attendanceappusers.adminapp.subject.adminsubject

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.adminapp.homescreen.subjectmanagement.SubjectManagementViewModel
import attendanceappusers.adminapp.subject.addschedule.AddScheduleDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.attendance.AttendanceToExportListHolder
import com.attendanceapp2.data.model.showToast
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UpdateSubject
import com.attendanceapp2.data.model.subject.UpdatingSubjectHolder
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.navigation.approutes.admin.AdminSubject
import com.attendanceapp2.theme.Purple40
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AdminSubjectScreen (
    navController: NavController,
    viewModel: AdminSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectManagement : SubjectManagementViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedDay by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
    val attendanceSummaries by viewModel.attendanceSummaries.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showArchiveDialog by remember { mutableStateOf(false) }
    var subjectToDelete by remember { mutableStateOf<Subject?>(null) }
    var subjectToArchive by remember { mutableStateOf<Subject?>(null) }
    val subjectSchedules = viewModel.subjectSchedules.collectAsState()
    var addScheduleDialog by remember { mutableStateOf(false) }
    var scheduleToDelete by remember { mutableStateOf<Schedule?>(null) }
    var showDeleteScheduleDialog by remember { mutableStateOf(false) }
    var showDownloadDialog by remember { mutableStateOf(false) }
    var hasWritePermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasWritePermission = granted
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    LaunchedEffect(selectedSubject) {
        selectedSubject?.let {
            viewModel.getSchedulesForSubjects(it.id)
            viewModel.getSubjectsCurrentMonthAttendances(it)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
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
                            modifier = Modifier
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column (
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(text = "Code/Name:", fontSize = 12.sp)
                                Text(text = "Faculty:", fontSize = 12.sp)
                                Text(text = "Room:", fontSize = 12.sp)
                                Text(text = "Join Code:", fontSize = 12.sp)
                            }

                            Column (
                                modifier = Modifier.weight(2f),
                            ) {
                                Text(text = "${selectedSubject.code} - ${selectedSubject.name}", fontSize = 12.sp)
                                Text(text = "${selectedSubject.faculty}", fontSize = 12.sp)
                                Text(text = selectedSubject.room, fontSize = 12.sp)
                                Text(text = selectedSubject.joinCode, fontSize = 12.sp)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                            verticalAlignment = Alignment.CenterVertically
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
                                contentColor = MaterialTheme.colorScheme.error,
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.Update, contentDescription = "Update")
                                    Text(text = "Update", fontSize = 6.sp)
                                }
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
                                contentColor = MaterialTheme.colorScheme.error,
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                    Text(text = "Delete", fontSize = 6.sp)
                                }
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
                                contentColor = MaterialTheme.colorScheme.error,
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Filled.Archive, contentDescription = "Archive")
                                    Text(text = "Archive", fontSize = 6.sp)
                                }
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
                    // Display schedules if available
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
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(2f)
                                )
                                Text(
                                    text = "${schedule.start} - ${schedule.end}",
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(3f)
                                )

                                Button(
                                    onClick = {
                                        scheduleToDelete = schedule
                                        showDeleteScheduleDialog = true
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        contentColor = MaterialTheme.colorScheme.error
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Schedule",
                                        modifier = Modifier
                                            .size(20.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    } else {
                        Text(text = "No schedules available")
                    }

                    // Add floating action button
                    FloatingActionButton(
                        onClick = { addScheduleDialog = true },
                        contentColor = MaterialTheme.colorScheme.error,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Add Schedule",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
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

                    val currentMonth =
                        LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    Text(
                        text = "Attendance Overview for the month of $currentMonth",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Student",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Absences",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Present",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Late",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }

                    // Sorted attendance summaries
                    val sortedAttendanceSummaries =
                        attendanceSummaries.values.sortedByDescending { it.absentCount }

                    sortedAttendanceSummaries.forEach { summary ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "${summary.firstname} ${summary.lastname}",
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxWidth()
                            )
                            Text(
                                text = summary.absentCount.toString(),
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                            Text(
                                text = summary.presentCount.toString(),
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                            Text(
                                text = summary.lateCount.toString(),
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { showDownloadDialog = true },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Download Attendances of this Subject",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(Icons.Filled.Download, contentDescription = "Download")
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
                    onClick = { navController.navigate(AdminHomeScreen.SubjectManagement.name) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
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
                    onClick = { navController.navigate(AdminSubject.SubjectAttendance.name) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
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

    AddScheduleDialog(
        selectedDay = selectedDay,
        startTime = startTime,
        endTime = endTime,
        onSelectedDayChange = { selectedDay = it },
        onStartTimeChange = { startTime = it },
        onEndTimeChange = { endTime = it },
        onDismiss = { addScheduleDialog = false },
        onAddSchedule = { selectedDay, startTime, endTime ->
            coroutineScope.launch {
                val addScheduleResult = viewModel.addScheduleOnline(
                    Schedule(
                        subjectId = selectedSubject!!.id,
                        subjectCode = selectedSubject.code,
                        subjectName = selectedSubject.name,
                        day = selectedDay,
                        start = startTime,
                        end = endTime
                    )
                )
                addScheduleResult.successMessage?.let {
                    addScheduleDialog = false
                    viewModel.getSchedulesForSubjects(selectedSubject.id)
                    showToast(context, it)
                }
                addScheduleResult.failureMessage?.let {
                    showToast(context, it)
                }
            }
        },
        showDialog = addScheduleDialog,
    )


    ConfirmDialog(
        title = "Delete Confirmation",
        message = "Are you sure you want to delete this subject?",
        onConfirm = {
            subjectToDelete?.let { subject ->
                subjectManagement.deleteSubject(subject)
                showDeleteDialog = false
                subjectToDelete = null
            }
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
        },
        onDismiss = {
            showArchiveDialog = false
            subjectToArchive = null
        },
        showDialog = showArchiveDialog
    )

    ConfirmDialog(
        title = "Delete Schedule Confirmation",
        message = "Are you sure you want to delete this schedule?",
        onConfirm = {
            scheduleToDelete?.let { schedule ->
                coroutineScope.launch {
                    viewModel.deleteSchedule(schedule)
                    viewModel.getSchedulesForSubjects(selectedSubject!!.id)
                }
                showDeleteScheduleDialog = false
                scheduleToDelete = null
            }
        },
        onDismiss = {
            showDeleteScheduleDialog = false
            scheduleToDelete = null
        },
        showDialog = showDeleteScheduleDialog
    )

    DownloadAttendanceDialog(
        onDismiss = { showDownloadDialog = false },
        onDownload = { period ->
            coroutineScope.launch {
                if (hasWritePermission) {
                    viewModel.getSubjectsAttendancesToExport(context, selectedSubject!!, period)
                } else {
                    launcher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                showDownloadDialog = false
            }
        },
        showDialog = showDownloadDialog,
        options = listOf("Current Month", "Previous Month", "Whole Year")
    )
}
