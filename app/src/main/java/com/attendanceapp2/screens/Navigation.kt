package com.attendanceapp2.screens

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
import com.attendanceapp2.screens.mainscreens.attendances.AttendanceScreen
import com.attendanceapp2.screens.mainscreens.scanner.Scanner
import com.attendanceapp2.screens.mainscreens.subjects.SubjectScreen
import com.attendanceapp2.screens.navigation.bottomNavBar.BottomNavBar
import com.attendanceapp2.screens.navigation.routes.MainRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = MainRoute.Subjects.name
            ) {
                composable(route = MainRoute.Subjects.name) {
                    SubjectScreen(navController)
                }

                composable(route = MainRoute.Attendances.name) {
                    AttendanceScreen(navController)
                }

                composable(route = MainRoute.Scanner.name) {
                    Scanner()
                }

                composable(route = MainRoute.Notifications.name) {

                }

                composable(route = MainRoute.Profile.name) {

                }
            }
        }
    }
}