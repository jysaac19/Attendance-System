package com.attendanceapp2.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attendanceapp2.navigation.approutes.student.StudentMainRoute


@Composable
fun BottomNavBar(
    navController: NavController,
    centerItem: Boolean,
    nonCenterItem: Boolean
) {
    val items = listOf(
        BottomNavItem(
            "Subjects",
            Icons.Default.Folder,
            Icons.Default.FolderOpen,
            StudentMainRoute.Subjects.name
        ),
        BottomNavItem(
            "Attendances",
            Icons.Filled.PieChart,
            Icons.Outlined.PieChart,
            StudentMainRoute.Attendances.name
        ),
        BottomNavItem(
            "Scanner",
            Icons.Filled.QrCode2,
            Icons.Outlined.QrCode2,
            StudentMainRoute.Scanner.name
        ),
        BottomNavItem(
            "Notifications",
            Icons.Filled.Notifications,
            Icons.Outlined.Notifications,
            StudentMainRoute.Notifications.name
        ),
        BottomNavItem(
            "Profile",
            Icons.Filled.Person,
            Icons.Outlined.Person,
            StudentMainRoute.Profile.name
        )
    )

    var selectedItem by remember { mutableIntStateOf(0) }
    val translationY by animateFloatAsState(
        targetValue = if (selectedItem == items.size / 2 && selectedItem == selectedItem) -120f else -80f,
        label = ""
    )

    // Conditionally render the Box based on centerItem and nonCenterItem
    if (centerItem || nonCenterItem) {
        Box(
            modifier = Modifier
                .background(Color.Red.copy(alpha = 0.10f))
                .padding(bottom = 40.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    if (index == items.size / 2 && centerItem) {
                        // If the item is in the middle and should be shown, show a FloatingActionButton
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
                    } else if (nonCenterItem) {
                        // If the item is not in the middle and should be shown, show a NavigationBarItem with title when selected
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(2.dp)
                                .weight(1f)
                        ) {
                            Button(
                                onClick = {
                                    selectedItem = index
                                    navController.navigate(item.route)
                                },
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .size(width = 70.dp, height = 38.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
//                                    if (index == selectedItem) {
//                                        Color.Black.copy(alpha = 0.3f)
//                                    } else {
//                                        Color.Transparent
//                                    },
                                    contentColor = LocalContentColor.current
                                )
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
                            if (index == selectedItem) {
                                Text(
                                    text = item.title,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}