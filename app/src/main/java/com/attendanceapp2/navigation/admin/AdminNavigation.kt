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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import attendanceappusers.adminapp.attendance.AdminAttendanceList
import attendanceappusers.adminapp.homescreen.HomeScreen
import attendanceappusers.adminapp.homescreen.usermanagement.adduser.AddUserScreen
import attendanceappusers.adminapp.homescreen.attendancemanagement.AttendanceManagementScreen
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.SearchSubjectScreen
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent.SearchStudentScreen
import attendanceappusers.adminapp.homescreen.subjectmanagement.SubjectManagementScreen
import attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject.AddSubjectScreen
import attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject.UpdateSubjectScreen
import attendanceappusers.adminapp.homescreen.usermanagement.UserManagementScreen
import attendanceappusers.adminapp.homescreen.usermanagement.updateuser.UpdateUserScreen
import attendanceappusers.adminapp.profile.AdminProfileScreen
import attendanceappusers.adminapp.subject.adminsubjectlist.AdminSubjectListScreen
import attendanceappusers.adminapp.subject.adminsubject.AdminSubjectScreen
import attendanceappusers.adminapp.subject.adminsubjectattendacne.AdminSubjectAttendanceScreen
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.navigation.approutes.admin.AdminMainRoute
import com.attendanceapp2.navigation.approutes.admin.AdminSubject

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
                startDestination = AdminMainRoute.Home.name
            ) {
                //Scope: MainScreens
                composable(route = AdminMainRoute.Attendances.name) {
                    AdminAttendanceList(navController)
                }
                composable(route = AdminMainRoute.Notification.name) {

                }
                composable(route = AdminMainRoute.Profile.name) {
                    AdminProfileScreen(navController)
                }

                //Scope: HomeScreen
                navigation(startDestination = AdminHomeScreen.HomeScreen.name, route = AdminMainRoute.Home.name){
                    composable(route = AdminHomeScreen.HomeScreen.name) {
                        HomeScreen(navController)
                    }
                    composable(route = AdminHomeScreen.AddSubject.name) {
                        AddSubjectScreen(navController)
                    }
                    composable(route = AdminHomeScreen.AddUser.name) {
                        AddUserScreen(navController)
                    }

                    composable(route = AdminHomeScreen.SearchSubject.name) {
                        SearchSubjectScreen(navController)
                    }
                    composable(route = AdminHomeScreen.SearchStudent.name) {
                        SearchStudentScreen(navController)
                    }

                    composable(route = AdminHomeScreen.UpdateUser.name) {
                        UpdateUserScreen(navController)
                    }
                    composable(route = AdminHomeScreen.UpdateSubject.name) {
                        UpdateSubjectScreen(navController)
                    }

                    composable(route = AdminHomeScreen.UserManagement.name) {
                        UserManagementScreen(navController)
                    }
                    composable(route = AdminHomeScreen.AttendanceManagement.name) {
                        AttendanceManagementScreen(navController)
                    }
                    composable(route = AdminHomeScreen.SubjectManagement.name) {
                        SubjectManagementScreen(navController)
                    }
                }

                //Scope : Subjects
                navigation(startDestination = AdminSubject.SubjectList.name, route = AdminMainRoute.Subjects.name) {
                    composable(route = AdminSubject.SubjectList.name) {
                        AdminSubjectListScreen(navController)
                    }
                    composable(route = AdminSubject.SubjectScreen.name) {
                        AdminSubjectScreen(navController)
                    }
                    composable(route = AdminSubject.AddSchedule.name) {

                    }
                    composable(route = AdminSubject.SubjectAttendance.name) {
                        AdminSubjectAttendanceScreen(navController)
                    }
                }
            }
        }
    }
}
