package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.approutes.AppRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = AppRoutes.AUTH.name
    ) {
        composable(route = AppRoutes.STUDENT.name) {
            StudentNavigation()
        }

        composable(route = AppRoutes.FACULTY.name) {
            FacultyNavigation()
        }

        composable(route = AppRoutes.ADMIN.name) {

        }
    }
}