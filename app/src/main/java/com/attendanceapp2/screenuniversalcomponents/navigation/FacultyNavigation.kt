package com.attendanceapp2.screenuniversalcomponents.navigation

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
import com.attendanceapp2.approutes.FacultyMainRoute
import com.attendanceapp2.screenuniversalcomponents.navigation.FacultyBottomNavBar
import com.attendanceapp2.users.facultyapp.screens.mainscreen.qrscreen.QRCode
import com.attendanceapp2.users.studentapp.screens.mainscreens.attendances.StudentAttendances
import com.attendanceapp2.users.studentapp.screens.mainscreens.subjects.StudentSubjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyNavigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    Scaffold(
        bottomBar = { FacultyBottomNavBar(navController = navController) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = FacultyMainRoute.Subjects.name
            ) {
                composable(route = FacultyMainRoute.Subjects.name) {
                    StudentSubjects(navController)
                }

                composable(route = FacultyMainRoute.Attendances.name) {
                    StudentAttendances(navController)
                }

                composable(route = FacultyMainRoute.Code.name) {
                    QRCode()
                }

                composable(route = FacultyMainRoute.Notifications.name) {

                }

                composable(route = FacultyMainRoute.Profile.name) {

                }
            }
        }
    }
}
