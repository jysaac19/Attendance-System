package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.approutes.AuthRoute
import com.attendanceapp2.authentication.SignInScreen
import com.attendanceapp2.authentication.SignUpScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthNavigation() {
    val navController: NavHostController = rememberNavController()
//    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = AuthRoute.SignIn.name
    ) {
        composable(route = AuthRoute.SignIn.name) {
            SignInScreen(navController)
        }

        composable(route = AuthRoute.SignUp.name) {
            SignUpScreen(navController)
        }
    }
}