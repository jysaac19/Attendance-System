package com.attendanceapp2.screens.navigation.bottomNavBar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.attendanceapp2.screens.navigation.routes.MainRoute







@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            "Subjects",
            Icons.Default.Folder,
            Icons.Default.FolderOpen,
            MainRoute.Subjects.name
        ),
        BottomNavItem(
            "Attendances",
            Icons.Filled.PieChart,
            Icons.Outlined.PieChart,
            MainRoute.Attendances.name
        ),
        BottomNavItem(
            "Scanner",
            Icons.Filled.QrCode2,
            Icons.Outlined.QrCode2,
            MainRoute.Scanner.name
        ),
        BottomNavItem(
            "Notifications",
            Icons.Filled.Notifications,
            Icons.Outlined.Notifications,
            MainRoute.Notifications.name
        ),
        BottomNavItem("Profile",
            Icons.Filled.Person,
            Icons.Outlined.Person,
            MainRoute.Profile.name
        )
    )

    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            if (index == items.size / 2) {
                // If the item is in the middle, show a FloatingActionButton
                FloatingActionButton(
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(75.dp),
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = item.selectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(45.dp)
                    )
                }
            } else {
                // Otherwise, show a NavigationBarItem
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    },
                    label = {
                        Text(text = item.title)
                    },
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItem) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    modifier = Modifier.padding(8.dp),
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.LightGray
                    )
                )
            }
        }
    }
}