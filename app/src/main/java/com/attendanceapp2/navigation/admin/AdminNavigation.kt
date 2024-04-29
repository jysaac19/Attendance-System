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
import androidx.navigation.navigation
import attendanceappusers.adminapp.attendance.AdminAttendanceList
import attendanceappusers.adminapp.homescreen.HomeScreen
import attendanceappusers.adminapp.homescreen.addsubject.AddSubjectScreen
import attendanceappusers.adminapp.homescreen.adduser.AddUserScreen
import attendanceappusers.adminapp.profile.AdminProfileScreen
import attendanceappusers.adminapp.subject.AdminSubjectScreen
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
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
                    HomeScreen(navController)
                }
                composable(route = AdminHomeScreen.AddSubject.name) {
                    AddSubjectScreen(navController)
                }
                composable(route = AdminHomeScreen.AddUser.name) {
                    AddUserScreen(navController)
                }
                composable(route = AdminMainRoute.Subjects.name) {
                    AdminSubjectScreen(navController)
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
