package attendanceappusers.adminapp.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen

@Composable
fun AdminHomeScreen(
    navController: NavController,
    viewModel: AdminHomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            onClick = { navController.navigate(AdminHomeScreen.UserManagement.name) },
            modifier = Modifier.size(300.dp, 180.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            HomescreenMenu(
                title = "User Management",
                description = "Manage Users",
                icon = Icons.Default.Group
            )
        }

        Card(
            onClick = { navController.navigate(AdminHomeScreen.AttendanceManagement.name) },
            modifier = Modifier.size(300.dp, 180.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            HomescreenMenu(
                title = "Attendance Management",
                description = "Manage Attendance",
                icon = Icons.Default.List
            )
        }

        Card(
            onClick = { navController.navigate(AdminHomeScreen.SubjectManagement.name) },
            modifier = Modifier.size(300.dp, 180.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            HomescreenMenu(
                title = "Subject Management",
                description = "Manage Subjects",
                icon = Icons.Default.School
            )
        }
    }
}

@Composable
fun HomescreenMenu(title: String, description: String, icon: ImageVector) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}