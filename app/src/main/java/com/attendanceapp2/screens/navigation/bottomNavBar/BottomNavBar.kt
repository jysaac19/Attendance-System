package com.attendanceapp2.screens.navigation.bottomNavBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        BottomNavItem(
            "Profile",
            Icons.Filled.Person,
            Icons.Outlined.Person,
            MainRoute.Profile.name
        )
    )

    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableIntStateOf(0) }
    val translationY by animateFloatAsState(
        targetValue = if (selectedItem == items.size / 2 && selectedItem == selectedItem) -150f else -80f,
        label = ""
    )

    Box (
        modifier = Modifier
            .background(Color.Red.copy(alpha = 0.10f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 45.dp)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                if (index == items.size / 2) {
                    // If the item is in the middle, show a FloatingActionButton
                    FloatingActionButton(
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route)
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .graphicsLayer(
                                translationY = translationY
                            ),
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        shape = CircleShape,
                        content = {
                            Icon(
                                imageVector = item.selectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(45.dp)
                            )
                        }
                    )
                } else {
                    // Otherwise, show a NavigationBarItem with title when selected
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.route)
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selectedItem) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                        AnimatedVisibility(
                            visible = index == selectedItem,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}