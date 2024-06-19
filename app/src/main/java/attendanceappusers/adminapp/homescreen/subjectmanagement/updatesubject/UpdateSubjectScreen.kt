package attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.adminapp.notification.NotificationViewModel
import com.attendanceapp2.R
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Notifications
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UpdateSubject
import com.attendanceapp2.data.model.subject.UpdatingSubjectHolder
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import kotlinx.coroutines.launch

@Composable
fun UpdateSubjectScreen(
    navController: NavController,
    viewModel: UpdateSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    notificationViewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var updateSubject by remember { mutableStateOf(UpdatingSubjectHolder.getSelectedSubject() ?: UpdateSubject(0, "", "", "", "", "", "")) }

    var result by remember { mutableStateOf(Results.UpdateSubjectResult()) }

    var showDialog by remember { mutableStateOf(false) }

    val facultyList by viewModel.facultyList.collectAsState(initial = emptyList())

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.nbslogo),
                contentDescription = "NBS Logo",
                modifier = Modifier.size(150.dp)
            )
        }

        item {
            OutlinedTextField(
                value = updateSubject.code,
                onValueChange = { updateSubject = updateSubject.copy(code = it.toUpperCase()) },
                singleLine = true,
                label = { Text("Subject Code") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = updateSubject.name,
                onValueChange = { updateSubject = updateSubject.copy(name = it.toUpperCase()) }, // Convert to uppercase
                label = { Text("Subject Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }
        item {
            UniversalDropDownMenu(
                label = "Room",
                items = listOf("RM401", "RM402", "RM403", "RM404", "RM405", "RM406", "RM407", "RM408", "RM409", "RM410", "RM411"),
                selectedItem = updateSubject.room,
                onItemSelected = { updateSubject = updateSubject.copy(room = it) }
            )

            UniversalDropDownMenu(
                label = "Faculty",
                items = facultyList.map { "${it.firstname} ${it.lastname}" },
                selectedItem = updateSubject.faculty,
                onItemSelected = { updateSubject = updateSubject.copy(faculty = it) }
            )
        }

        item {
            result.failureMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
            ) {
                FloatingActionButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Cancel"
                        )

                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                FloatingActionButton(
                    onClick = {
                        result = viewModel.validateFields(
                            Subject(
                                updateSubject.id,
                                updateSubject.code,
                                updateSubject.name,
                                updateSubject.room,
                                updateSubject.faculty,
                                updateSubject.subjectStatus,
                                updateSubject.joinCode
                            )
                        )
                        if (result.failureMessage == null) {
                            coroutineScope.launch {
                                showDialog = true
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save"
                        )
                    }
                }
            }
        }
    }

    ConfirmDialog(
        title = "Confirmation",
        message = "Are you sure you want to update this subject?",
        onConfirm = {
            // If confirmed, proceed with updating the subject
            coroutineScope.launch {
                val oldFaculty = SelectedSubjectHolder.getSelectedSubject()?.faculty ?: ""
                viewModel.updateSubject(
                    Subject(
                        updateSubject.id,
                        updateSubject.code,
                        updateSubject.name,
                        updateSubject.room,
                        updateSubject.faculty,
                        updateSubject.subjectStatus,
                        updateSubject.joinCode
                    ),
                    oldFaculty
                )
                notificationViewModel.insertNotifications(Notifications(title = "${updateSubject.code} has been updated!", message = "Your subject details has been changed! Thank you.", portal = "admin"))
                navController.navigate(AdminHomeScreen.SubjectManagement.name)
            }


        },
        onDismiss = {
            // If dismissed, set the showDialog flag to false
            showDialog = false
        },
        showDialog = showDialog
    )
}