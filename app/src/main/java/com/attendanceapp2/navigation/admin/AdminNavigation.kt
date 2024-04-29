package com.attendanceapp2.navigation.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import attendanceappusers.adminapp.attendance.AdminAttendanceList
import attendanceappusers.adminapp.profile.AdminProfileScreen
import com.attendanceapp2.navigation.approutes.admin.AdminMainRoute
import com.attendanceapp2.navigation.faculty.FacultyBottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNavigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    var centerItem by remember { mutableStateOf(true) }
    var nonCenterItem by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { AdminBottomNavBar(navController = navController) }

    ) {
        Box(modifier = Modifier.padding(it)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = AdminMainRoute.HomeScreen.name
            ) {
                composable(route = AdminMainRoute.HomeScreen.name) {

                }
                composable(route = AdminMainRoute.Subjects.name) {

                }
                composable(route = AdminMainRoute.Attendances.name) {
                    AdminAttendanceList(navController)
                }
                composable(route = AdminMainRoute.Notification.name) {

                }
                composable(route = AdminMainRoute.Profile.name) {
                    AdminProfileScreen()
                }
            }
        }
    }
}
