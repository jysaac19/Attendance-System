package com.attendanceapp2.screenuniversalcomponents.navigation.student

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.approutes.StudentMainRoute
import com.attendanceapp2.screenuniversalcomponents.ProfileScreen
import com.attendanceapp2.users.studentapp.screens.mainscreens.attendances.StudentAttendances
import com.attendanceapp2.users.studentapp.screens.mainscreens.scanner.StudentScanner
import com.attendanceapp2.users.studentapp.screens.mainscreens.subjects.StudentSubjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentNavigation() {
    val navController: NavHostController = rememberNavController()
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

            bottomBar = { StudentBottomNavBar(navController = navController) }

        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = StudentMainRoute.Subjects.name,
                modifier = Modifier.padding(it)
            ) {
                composable(route = StudentMainRoute.Subjects.name) {
                    StudentSubjects(navController)
                }

                composable(route = StudentMainRoute.Attendances.name) {
                    StudentAttendances(navController)
                }

                composable(route = StudentMainRoute.Scanner.name) {
                    StudentScanner()
                }

                composable(route = StudentMainRoute.Notifications.name) {

                }

                composable(route = StudentMainRoute.Profile.name) {
                    ProfileScreen()
                }
            }

        }
    }
}
