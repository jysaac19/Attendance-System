package com.attendanceapp2.universalscreencomponents.navigation.faculty

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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.attendanceapp2.approutes.faculty.FacultyMainRoute
import com.attendanceapp2.approutes.faculty.FacultySubjects
import com.attendanceapp2.universalscreencomponents.ProfileScreen
import com.attendanceapp2.users.facultyapp.screens.mainscreen.attendances.FacultyAttendances
import com.attendanceapp2.users.facultyapp.screens.mainscreen.qrscreen.QRCode
import com.attendanceapp2.users.facultyapp.screens.mainscreen.subjects.FacultySubjects

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
                    FacultySubjects(navController)
                }
                navigation(startDestination = FacultyMainRoute.Subjects.name, route = FacultySubjects.MainSubjectScreen.name) {
                    composable(route = FacultyMainRoute.Subjects.name) {
                        FacultySubjects(navController)
                    }
                    composable(route = FacultySubjects.SubjectAttendances.name) {
                        FacultyAttendances(navController)
                    }
                }
                composable(route = FacultyMainRoute.Attendances.name) {
                    FacultyAttendances(navController)
                }

                composable(route = FacultyMainRoute.Code.name) {
                    QRCode(navController)
                }

                composable(route = FacultyMainRoute.Notifications.name) {

                }

                composable(route = FacultyMainRoute.Profile.name) {
                    ProfileScreen()
                }
            }
        }
    }
}
