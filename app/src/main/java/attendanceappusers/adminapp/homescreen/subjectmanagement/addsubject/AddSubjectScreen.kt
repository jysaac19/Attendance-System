package attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject

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
import attendanceappusers.adminapp.homescreen.subjectmanagement.SubjectManagementViewModel
import attendanceappusers.notification.NotificationViewModel
import com.attendanceapp2.R
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Notifications
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import kotlinx.coroutines.launch

@Composable
fun AddSubjectScreen(
    navController: NavController,
    viewModel: AddSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectManagement: SubjectManagementViewModel = viewModel(factory = AppViewModelProvider.Factory),
    notificationViewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var subjectCode by remember { mutableStateOf("") }
    var subjectName by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var selectedFaculty by remember { mutableStateOf("") }

    var result by remember { mutableStateOf(Results.AddSubjectResult()) }

    var showDialog by remember { mutableStateOf(false) }

    val rooms = listOf(
        "RM401",
        "RM402",
        "RM403",
        "RM404",
        "RM405",
        "RM406",
        "RM407",
        "RM408",
        "RM409",
        "RM410",
        "RM411"
    )

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
            Text(
                "Welcome to the Add Subject Screen",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                "Use this screen to add a new subject to the system.",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            OutlinedTextField(
                value = subjectCode,
                onValueChange = { subjectCode = it.uppercase() },
                singleLine = true,
                label = { Text("Subject Code") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = subjectName,
                onValueChange = { subjectName = it.uppercase() },
                label = { Text("Subject Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }
        item {
            UniversalDropDownMenu(
                label = "Room",
                items = rooms,
                selectedItem = selectedRoom,
                onItemSelected = { selectedRoom = it }
            )

            UniversalDropDownMenu(
                label = "Faculty",
                items = facultyList.map { "${it.firstname} ${it.lastname}" },
                selectedItem = selectedFaculty,
                onItemSelected = { selectedFaculty = it }
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
                        coroutineScope.launch {
                            result = viewModel.saveSubject(
                                subjectCode,
                                subjectName,
                                selectedRoom,
                                selectedFaculty
                            )
                            if (result.successMessage != null) {
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
        message = "Are you sure you want to save this subject?",
        onConfirm = {
            coroutineScope.launch {
                viewModel.saveSubject(
                    subjectCode,
                    subjectName,
                    selectedRoom,
                    selectedFaculty
                )
                notificationViewModel.insertNotifications(Notifications(title = "$subjectCode has been added!", message = "$subjectName has been added to the course list", portal = "admin"))
            }
            navController.navigate(AdminHomeScreen.SubjectManagement.name)
        },
        onDismiss = {
            showDialog = false
        },
        showDialog = showDialog
    )
}