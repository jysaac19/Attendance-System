package com.attendanceapp2.universal.screencomponents.navigation.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.attendanceapp2.approutes.faculty.FacultyMainRoute
import com.attendanceapp2.approutes.student.StudentMainRoute
import com.attendanceapp2.approutes.student.StudentSubjectsRoutes
import com.attendanceapp2.universal.screencomponents.ProfileScreen
import com.attendanceapp2.users.studentapp.screens.mainscreens.StudentAttendances
import com.attendanceapp2.users.studentapp.screens.mainscreens.StudentScanner
import com.attendanceapp2.users.studentapp.screens.mainscreens.StudentSubjects
import com.attendanceapp2.users.studentapp.screens.subjects.StudentSubjectAttendances

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentNavigation(
    userId: Long,

) {
    val navController = rememberNavController()
    var centerItem by remember { mutableStateOf(true) }
    var nonCenterItem by remember { mutableStateOf(true) }


    var open by remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    var openHelp by remember {
        mutableStateOf(false)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ModalNavigationDrawer(
        drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier
                .background(Color.White)
                .fillMaxHeight()
        ) {

        }
    },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {


        Scaffold(
            bottomBar = {
                StudentBottomNavBar(
                    navController = navController,
                    centerItem = centerItem,
                    nonCenterItem = nonCenterItem
                )
            }

        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = StudentMainRoute.Subjects.name,
                modifier = Modifier.padding(it)
            ) {
                composable(route = StudentMainRoute.Subjects.name) {
                    StudentSubjects(navController)
                    centerItem = true
                    nonCenterItem = true
                }
                navigation(startDestination = StudentMainRoute.Subjects.name, route = StudentSubjectsRoutes.StudentMainSubjectScreen.name) {
                    composable(route = FacultyMainRoute.Subjects.name) {
                        StudentSubjects(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                    composable(route = StudentSubjectsRoutes.StudentSubjectAttendances.name) {
                        StudentSubjectAttendances(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                }
                composable(route = StudentMainRoute.Attendances.name) {
                    StudentAttendances(navController = navController, userId = userId)
                    centerItem = true
                    nonCenterItem = true
                }

                composable(route = StudentMainRoute.Scanner.name) {
                    StudentScanner()
                    centerItem = true
                    nonCenterItem = true
                }

                composable(route = StudentMainRoute.Notifications.name) {

                }

                composable(route = StudentMainRoute.Profile.name) {
                    ProfileScreen()
                    centerItem = true
                    nonCenterItem = true
                }
            }

        }
    }
}