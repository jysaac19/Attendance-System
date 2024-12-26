package attendanceappusers.adminapp.homescreen.usermanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.showToast
import com.attendanceapp2.data.model.user.UpdateUser
import com.attendanceapp2.data.model.user.UpdatingUserHolder
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagementScreen(
    navController: NavController,
    viewModel: UserManagementViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val users by viewModel.users.collectAsState()

    var query by remember { mutableStateOf("") }

    var selectedUserType by remember { mutableStateOf("All") }
    val userType = listOf("All", "Admin", "Student", "Faculty")
    var expanded by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDeactivateDialog by remember { mutableStateOf(false) }
    var userToDelete: User? by remember { mutableStateOf(null) }
    var userToDeactivate: User? by remember { mutableStateOf(null) }
    var showReactivateDialog by remember { mutableStateOf(false) }
    var userToReactivate: User? by remember { mutableStateOf(null) }

    val handleReactivateAction: () -> Unit = {
        userToReactivate?.let { user ->
            coroutineScope.launch {
                viewModel.reactivateUser(user)
            }
        }
    }

    // Function to handle delete action
    val handleDeleteAction: () -> Unit = {
        userToDelete?.let { user ->
            coroutineScope.launch {
                viewModel.deleteUser(user)
            }
        }
    }

    // Function to handle deactivate action
    val handleDeactivateAction: () -> Unit = {
        userToDeactivate?.let { user ->
            coroutineScope.launch {
                viewModel.deactivateUser(user)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "User Management",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
            },
            label = { Text("Search") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { /* TODO: Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {Text("Enter User ID or Name")}
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedUserType,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                userType.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedUserType = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(AdminHomeScreen.HomeScreen.name) },
                modifier = Modifier
                    .padding(end = 2.dp, top = 4.dp, bottom = 4.dp)
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ){
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)

                    Text(
                        text = "Back",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Button(
                onClick = { navController.navigate(AdminHomeScreen.AddUser.name) },
                modifier = Modifier
                    .padding(start = 2.dp, top = 4.dp, bottom = 4.dp)
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add New User",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }

        LazyColumn {
            items(users) { user ->
                UserCard(
                    user = user,
                    onDeleteClick = {
                        userToDelete = user
                        showDeleteDialog = true
                    },
                    onDeactivateClick = {
                        userToDeactivate = user
                        showDeactivateDialog = true
                    },
                    onReactivateClick = {
                        userToReactivate = user
                        showReactivateDialog = true
                    },
                    onUpdateClick = {
                        coroutineScope.launch {
                            UpdatingUserHolder.setSelectedUser(
                                UpdateUser(
                                    user.id,
                                    user.firstname,
                                    user.lastname,
                                    user.email,
                                    user.password,
                                    user.usertype,
                                    user.department,
                                    user.status
                                )
                            )
                            navController.navigate(AdminHomeScreen.UpdateUser.name)
                        }
                    }
                )
            }
        }
    }

    ConfirmDialog(
        title = "Confirm Delete",
        message = "Are you sure you want to delete this user?",
        onConfirm = {
            handleDeleteAction()
            showDeleteDialog = false
            viewModel.updateOfflineUsers()
            showToast(context, "User Deleted")
        },
        onDismiss = { showDeleteDialog = false },
        showDialog = showDeleteDialog
    )

    // Confirmation dialog for deactivate action
    ConfirmDialog(
        title = "Confirm Deactivate",
        message = "Are you sure you want to deactivate this user?",
        onConfirm = {
            handleDeactivateAction()
            showDeactivateDialog = false
            viewModel.updateOfflineUsers()
            showToast(context, "User Deactivated")
        },
        onDismiss = { showDeactivateDialog = false },
        showDialog = showDeactivateDialog
    )

    ConfirmDialog(
        title = "Confirm Reactivate",
        message = "Are you sure you want to reactivate this user?",
        onConfirm = {
            handleReactivateAction()
            showReactivateDialog = false
            viewModel.updateOfflineUsers()
            showToast(context, "User Reactivated")
        },
        onDismiss = { showReactivateDialog = false },
        showDialog = showReactivateDialog
    )

    LaunchedEffect(query, selectedUserType) {
        viewModel.filterUsersByAdmin(
            searchQuery = query,
            userType = selectedUserType
        )
    }
}