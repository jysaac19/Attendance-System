package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.attendanceapp2.approutes.AppRoutes
import com.attendanceapp2.approutes.AuthRoute
import com.attendanceapp2.approutes.FacultyMainRoute
import com.attendanceapp2.approutes.StudentMainRoute
import com.attendanceapp2.authentication.SignInScreen
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpScreen
import com.attendanceapp2.users.facultyapp.screens.mainscreen.qrscreen.QRCode
import com.attendanceapp2.users.studentapp.screens.mainscreens.attendances.StudentAttendances
import com.attendanceapp2.users.studentapp.screens.mainscreens.scanner.StudentScanner
import com.attendanceapp2.users.studentapp.screens.mainscreens.subjects.StudentSubjects
import com.attendanceapp2.viewmodel.AppViewModelProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val signInViewModel : SignInViewModel = viewModel(factory = AppViewModelProvider.Factory)
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = AppRoutes.AUTH.name
    ) {
        composable(route = AppRoutes.AUTH.name) {
            AuthNavigation()
        }
        navigation(
            route = AppRoutes.AUTH.name,
            startDestination = AuthRoute.SignIn.name
        ) {
            composable(route = AuthRoute.SignIn.name) {
                SignInScreen(navController)
            }

            composable(route = AuthRoute.SignUp.name) {
                SignUpScreen(navController)
            }
        }

        composable(route = AppRoutes.STUDENT.name) {
            StudentNavigation()
        }
        navigation(
            route = AppRoutes.STUDENT.name,
            startDestination = StudentMainRoute.Subjects.name
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

            }
        }


        composable(route = AppRoutes.FACULTY.name) {
            FacultyNavigation()
        }
        navigation(
            route = AppRoutes.FACULTY.name,
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

        composable(route = AppRoutes.ADMIN.name) {
            Text(text = "Admin")
        }

        composable(route = "test") {
            Text(text = "Test")
        }
    }
}