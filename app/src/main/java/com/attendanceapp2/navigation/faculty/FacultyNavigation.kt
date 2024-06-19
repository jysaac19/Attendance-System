package com.attendanceapp2.navigation.faculty

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
import attendanceappusers.facultyapp.attendances.FacultyAttendances
import attendanceappusers.facultyapp.profile.ProfileScreen
import attendanceappusers.facultyapp.qrscreen.QRGeneratorScreen
import attendanceappusers.facultyapp.subjects.facultysubjectattendances.FacultySubjectAttendances
import attendanceappusers.facultyapp.subjects.facultysubjetctlist.FacultyActiveSubjects
import attendanceappusers.facultyapp.subjects.facultysubjetctlist.FacultyArchivedSubjects
import attendanceappusers.facultyapp.subjects.searchstudent.FacultySearchStudentScreen
import attendanceappusers.facultyapp.subjects.subjectinfo.FacultySubjectScreen
import com.attendanceapp2.navigation.approutes.faculty.FacultyMainRoute
import com.attendanceapp2.navigation.approutes.faculty.FacultySubjectsRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyNavigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    var centerItem by remember { mutableStateOf(true) }
    var nonCenterItem by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { FacultyBottomNavBar(
                navController = navController,
                centerItem = centerItem,
                nonCenterItem = nonCenterItem
            )
        }

    ) {
        Box(modifier = Modifier.padding(it)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = FacultyMainRoute.Subjects.name
            ) {
                composable(route = FacultyMainRoute.Subjects.name) {
                    FacultyActiveSubjects(navController)
                    centerItem = false
                    nonCenterItem = true
                }
                navigation(startDestination = FacultyMainRoute.Subjects.name, route = FacultySubjectsRoutes.FacultyMainSubjectScreen.name) {
                    composable(route = FacultyMainRoute.Subjects.name) {
                        FacultyActiveSubjects(navController)
                        centerItem = false
                        nonCenterItem = true
                    }
                    composable(route = FacultySubjectsRoutes.FacultyArchivedSubjects.name) {
                        FacultyArchivedSubjects(navController)
                        centerItem = false
                        nonCenterItem = true
                    }
                    composable(route = FacultySubjectsRoutes.FacultySubjectInfo.name) {
                        FacultySubjectScreen(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                    composable(route = FacultySubjectsRoutes.FacultySubjectAttendances.name) {
                        FacultySubjectAttendances(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                    composable(route = FacultySubjectsRoutes.FacultySearchStudents.name) {
                        FacultySearchStudentScreen(navController)
                        centerItem = false
                        nonCenterItem = true
                    }
                }
                composable(route = FacultyMainRoute.Attendances.name) {
                    FacultyAttendances(navController)
                    centerItem = false
                    nonCenterItem = true
                }

                composable(route = FacultyMainRoute.Code.name) {
                    QRGeneratorScreen(navController)
                    centerItem = true
                    nonCenterItem = true
                }

                composable(route = FacultyMainRoute.Notifications.name) {

                }

                composable(route = FacultyMainRoute.Profile.name) {
                    ProfileScreen()
                    centerItem = false
                    nonCenterItem = true
                }
            }
        }
    }
}
