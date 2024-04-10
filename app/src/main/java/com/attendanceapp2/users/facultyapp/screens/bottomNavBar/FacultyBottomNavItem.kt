package com.attendanceapp2.users.facultyapp.screens.bottomNavBar

import androidx.compose.ui.graphics.vector.ImageVector

data class FacultyBottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
 )