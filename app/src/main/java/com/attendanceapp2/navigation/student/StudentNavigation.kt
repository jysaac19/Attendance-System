package com.attendanceapp2.navigation.student

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import attendanceappusers.facultyapp.profile.ProfileScreen
import attendanceappusers.studentapp.attendance.StudentAttendances
import attendanceappusers.studentapp.scanner.StudentScanner
import attendanceappusers.studentapp.subjects.subjectattendances.StudentSubjectAttendances
import attendanceappusers.studentapp.subjects.subjectinfo.StudentSubjectInfo
import attendanceappusers.studentapp.subjects.subjectlist.StudentActiveSubjects
import attendanceappusers.studentapp.subjects.subjectlist.StudentArchivedSubjects
import com.attendanceapp2.MainActivity
import com.attendanceapp2.navigation.approutes.student.StudentMainRoute
import com.attendanceapp2.navigation.approutes.student.StudentSubjectsRoutes
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentNavigation() {
    val navController = rememberNavController()
    var centerItem by remember { mutableStateOf(true) }
    var nonCenterItem by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val geofencingClient = remember { LocationServices.getGeofencingClient(context) }
    println(geofencingClient)

        LaunchedEffect(geofencingClient) {

            val geofence = Geofence.Builder()
                .setRequestId("myGeofence")
                .setCircularRegion(14.706033,121.093593,10f)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
            val geofencingRequest = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build()
            if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val intent = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                geofencingClient.addGeofences(geofencingRequest, intent)
            }


        }



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
                    StudentActiveSubjects(navController)
                    centerItem = true
                    nonCenterItem = true
                }
                navigation(startDestination = StudentMainRoute.Subjects.name, route = StudentSubjectsRoutes.StudentMainSubjectScreen.name) {
                    composable(route = StudentMainRoute.Subjects.name) {
                        StudentActiveSubjects(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                    composable(route = StudentSubjectsRoutes.StudentArchivedSubjects.name) {
                        StudentArchivedSubjects(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                    composable(route = StudentSubjectsRoutes.StudentSubjectAttendances.name) {
                        StudentSubjectAttendances(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                    composable(route = StudentSubjectsRoutes.StudentSubjectInformation.name) {
                        StudentSubjectInfo(navController)
                        centerItem = true
                        nonCenterItem = true
                    }
                }
                composable(route = StudentMainRoute.Attendances.name) {
                    StudentAttendances(navController = navController)
                    centerItem = true
                    nonCenterItem = true
                }

                composable(route = StudentMainRoute.Scanner.name) {
                    StudentScanner()
                    centerItem = true
                    nonCenterItem = true
                }

                composable(route = StudentMainRoute.Notifications.name) {
//                    Notification(navController = navController)
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
