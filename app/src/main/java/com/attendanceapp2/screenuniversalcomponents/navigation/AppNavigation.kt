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
import com.attendanceapp2.authentication.SignInScreen
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpScreen
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

        composable(route = AppRoutes.STUDENT.name) {
            StudentNavigation()
        }

        composable(route = AppRoutes.FACULTY.name) {
            FacultyNavigation()
        }

        composable(route = AppRoutes.ADMIN.name) {
            Text(text = "Admin")
        }

        composable(route = "test") {
            Text(text = "Test")
        }
    }
}