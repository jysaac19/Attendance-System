package attendanceappusers.adminapp.homescreen.usermanagement.adduser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.notification.NotificationViewModel
import com.attendanceapp2.R
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Notifications
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import kotlinx.coroutines.launch

@Composable
fun AddUserScreen(
    navController: NavController,
    viewModel: AddUserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    notificationViewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("BSCS") }
    var selectedFaculty by remember { mutableStateOf("Student") }

    var showDialog by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(Results.AddUserResult()) }

    val department = listOf("BSCS", "BSA", "BSAIS", "BSE", "BSTM")
    val usertype = listOf("Student", "Faculty", "Admin")

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

            Text(
                "Welcome to the Add User Screen",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                "Use this screen to add a new user to the system.",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            OutlinedTextField(
                value = firstname,
                onValueChange = { firstname = it.uppercase() }, // Convert to uppercase
                singleLine = true,
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = lastname,
                onValueChange = { lastname = it.uppercase() }, // Convert to uppercase
                singleLine = true,
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        item {
            UniversalDropDownMenu(
                label = "Department",
                items = department,
                selectedItem = selectedRoom,
                onItemSelected = { selectedRoom = it }
            )

            UniversalDropDownMenu(
                label = "User Type",
                items = usertype,
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
                    onClick = {
                        navController.navigate(AdminHomeScreen.UserManagement.name)
                    },
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
                            result = viewModel.registerUser(firstname, lastname, email, password, selectedFaculty, selectedRoom, "Active")
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


    if (showDialog) {
        ConfirmDialog(
            title = "Confirmation",
            message = "Are you sure you want to save this user?",
            onConfirm = {
                coroutineScope.launch {
                    result = viewModel.registerUser(firstname, lastname, email, password, selectedFaculty, selectedRoom, "Active")
                    notificationViewModel.insertNotifications(Notifications(title = "New user has been added!", message = "$firstname $lastname has been added.", portal = "admin"))

                    navController.navigate(AdminHomeScreen.UserManagement.name)
                }

            },
            onDismiss = {
                showDialog = false
            },
            showDialog = showDialog
        )
    }
}