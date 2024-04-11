package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class StudentBottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
 )