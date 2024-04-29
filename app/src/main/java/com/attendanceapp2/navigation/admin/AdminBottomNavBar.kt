package com.attendanceapp2.navigation.admin

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
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
import com.attendanceapp2.navigation.approutes.admin.AdminMainRoute
import com.attendanceapp2.navigation.approutes.faculty.FacultyMainRoute


@Composable
fun AdminBottomNavBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem(
            "Home",
            Icons.Default.Home,
            Icons.Outlined.Home,
            AdminMainRoute.HomeScreen.name
        ),
        BottomNavItem(
            "Subjects",
            Icons.Filled.Folder,
            Icons.Outlined.Folder,
            AdminMainRoute.Subjects.name
        ),
        BottomNavItem(
            "Attendances",
            Icons.Filled.PieChart,
            Icons.Outlined.PieChart,
            AdminMainRoute.Attendances.name
        ),
        BottomNavItem(
            "Notifications",
            Icons.Filled.Notifications,
            Icons.Outlined.Notifications,
            AdminMainRoute.Notification.name
        ),
        BottomNavItem(
            "Profile",
            Icons.Filled.Person,
            Icons.Outlined.Person,
            AdminMainRoute.Profile.name
        )
    )

    var selectedItem by remember { mutableIntStateOf(0) }
    val translationY by animateFloatAsState(
        targetValue = if (selectedItem == items.size / 2 && selectedItem == selectedItem) -120f else -80f,
        label = ""
    )

    // Conditionally render the Box based on centerItem and nonCenterItem
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