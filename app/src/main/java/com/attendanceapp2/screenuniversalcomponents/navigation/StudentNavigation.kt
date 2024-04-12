package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Help
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.approutes.StudentMainRoute
import com.attendanceapp2.users.studentapp.screens.mainscreens.attendances.StudentAttendances
import com.attendanceapp2.screenuniversalcomponents.ProfileScreen
import com.attendanceapp2.users.studentapp.screens.mainscreens.scanner.StudentScanner
import com.attendanceapp2.users.studentapp.screens.mainscreens.subjects.StudentSubjects
import kotlinx.coroutines.launch

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
            bottomBar = { StudentBottomNavBar(navController = navController) },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Student Attendance",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier
                                .padding(40.dp)
                                .clickable {
                                    scope.launch {
                                        drawerState.apply {
                                            if (open) open() else close()
                                        }
                                    }
                                }
                                .size(30.dp),
                            tint = Color.Red
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Help,
                            contentDescription = "Help",
                            modifier = Modifier
                                .padding(40.dp)
                                .clickable {
                                    openHelp = !openHelp
                                }
                                .size(30.dp)
                        )
                        if (openHelp) {
                            Dialog(
                                onDismissRequest = { openHelp = false },
                                properties = DialogProperties(
                                    dismissOnBackPress = openHelp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                        .fillMaxSize(0.9f)
                                ) {

                                }
                            }
                        }

                    }
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
