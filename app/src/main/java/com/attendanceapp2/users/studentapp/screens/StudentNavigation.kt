package com.attendanceapp2.users.studentapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.users.studentapp.screens.mainscreens.attendances.AttendanceScreen
import com.attendanceapp2.users.studentapp.screens.mainscreens.scanner.Scanner
import com.attendanceapp2.users.studentapp.screens.mainscreens.subjects.SubjectScreen
import com.attendanceapp2.users.studentapp.screens.navigation.bottomNavBar.BottomNavBar
import com.attendanceapp2.approutes.StudentMainRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentNavigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = StudentMainRoute.Subjects.name
            ) {
                composable(route = StudentMainRoute.Subjects.name) {
                    SubjectScreen(navController)
                }

                composable(route = StudentMainRoute.Attendances.name) {
                    AttendanceScreen(navController)
                }

                composable(route = StudentMainRoute.Scanner.name) {
                    Scanner()
                }

                composable(route = StudentMainRoute.Notifications.name) {

                }

                composable(route = StudentMainRoute.Profile.name) {

                }
            }
        }
    }
}