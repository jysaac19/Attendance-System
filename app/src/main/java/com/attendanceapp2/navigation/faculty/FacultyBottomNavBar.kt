package com.attendanceapp2.navigation.faculty

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
import com.attendanceapp2.navigation.BottomNavItem
import com.attendanceapp2.navigation.approutes.faculty.FacultyMainRoute

@Composable
fun FacultyBottomNavBar(
    navController: NavController,
    centerItem: Boolean,
    nonCenterItem: Boolean
) {
    val items = listOf(
        BottomNavItem(
            "Subjects",
            Icons.Default.Folder,
            Icons.Default.FolderOpen,
            FacultyMainRoute.Subjects.name
        ),
        BottomNavItem(
            "Attendances",
            Icons.Filled.PieChart,
            Icons.Outlined.PieChart,
            FacultyMainRoute.Attendances.name
        ),
        BottomNavItem(
            "Scanner",
            Icons.Filled.QrCode2,
            Icons.Outlined.QrCode2,
            FacultyMainRoute.Code.name
        ),
        BottomNavItem(
            "Notifications",
            Icons.Filled.Notifications,
            Icons.Outlined.Notifications,
            FacultyMainRoute.Notifications.name
        ),
        BottomNavItem(
            "Profile",
            Icons.Filled.Person,
            Icons.Outlined.Person,
            FacultyMainRoute.Profile.name
        )
    )

    var selectedItem by remember { mutableIntStateOf(0) }
    val translationY by animateFloatAsState(
        targetValue = if (selectedItem == items.size / 2 && centerItem) -120f else -80f,
        label = ""
    )

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
                    // Render the center FloatingActionButton only if centerItem is true
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
                } else if (index != items.size / 2 && nonCenterItem) {
                    // Render other items if nonCenterItem is true
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
                                contentColor = LocalContentColor.current
                            )
                        ) {
                            Icon(
                                imageVector = if (index == selectedItem) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        if (index == selectedItem) {
                            Text(
                                text = item.title,
                                fontSize = 8.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}