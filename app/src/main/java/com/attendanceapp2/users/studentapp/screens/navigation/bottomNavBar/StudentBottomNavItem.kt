package com.attendanceapp2.users.studentapp.screens.navigation.bottomNavBar

import androidx.compose.ui.graphics.vector.ImageVector

data class StudentBottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
 )