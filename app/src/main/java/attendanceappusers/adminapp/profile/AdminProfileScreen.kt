package attendanceappusers.adminapp.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.appviewmodel.screenviewmodel.ProfileViewModel
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import kotlinx.coroutines.launch

@Composable
fun AdminProfileScreen (
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val (showLogoutDialog, setShowLogoutDialog) = remember { mutableStateOf(false) }
    val (showDeactivateDialog, setShowDeactivateDialog) = remember { mutableStateOf(false) }
    val (showDeleteDialog, setShowDeleteDialog) = remember { mutableStateOf(false) }
    val loggedInUser = LoggedInUserHolder.getLoggedInUser()

    if (loggedInUser != null) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .offset(y = (0.2f * LocalConfiguration.current.screenHeightDp).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                tint = Color.Gray,
                modifier = Modifier
                    .size(200.dp)
            )
            Text(
                text = "${loggedInUser.firstname} ${loggedInUser.lastname}",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = loggedInUser.email,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "${loggedInUser.department} Department",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = loggedInUser.usertype,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )


            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                FloatingActionButton(
                    onClick = { setShowLogoutDialog(true) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                FloatingActionButton(
                    onClick = { setShowLogoutDialog(true) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Deactivate Account",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Icon(
                            imageVector = Icons.Default.Mail,
                            contentDescription = "Email",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                FloatingActionButton(
                    onClick = { setShowLogoutDialog(true) },
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Delete Account",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Icon(
                            imageVector = Icons.Default.Mail,
                            contentDescription = "Email",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        ConfirmDialog(
            title = "Logout",
            message = "Are you sure you want to logout?",
            onConfirm = {
                coroutineScope.launch {
                    viewModel.logout()
                }

            },
            onDismiss = { setShowLogoutDialog(false) },
            showDialog = showLogoutDialog
        )

        ConfirmDialog(
            title = "Deactivate Account",
            message = "Are you sure you want to deactivate your account?",
            onConfirm = {
                coroutineScope.launch {
                    viewModel.deactivate()
                    viewModel.logout()
                }
            },
            onDismiss = { setShowDeactivateDialog(false) },
            showDialog = showDeactivateDialog
        )

        ConfirmDialog(
            title = "Delete Account",
            message = "Are you sure you want to delete your account?",
            onConfirm = {
                coroutineScope.launch {
                    viewModel.delete()
                }
            },
            onDismiss = { setShowDeleteDialog(false) },
            showDialog = showDeleteDialog
        )
    } else {
        Text(text = "No user is currently logged in.", modifier = Modifier.padding(16.dp))
    }
}