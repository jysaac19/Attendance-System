package com.attendanceapp2.screens.mainscreens.attendances

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.screens.mainscreens.attendances.attendancescreencomponents.AttendanceCard
import com.attendanceapp2.screens.mainscreens.attendances.attendancescreencomponents.AttendanceColumnName
import com.attendanceapp2.screens.mainscreens.attendances.attendancescreencomponents.CustomDatePicker

@Composable
fun AttendanceScreen (navController : NavController) {
    val attendances = listOf(
        Attendance(235, "Kenneth", "Bonaagua", "Mathematics", "MATH101", "2024-03-29", "08:00 AM", "2023-2024", 1),
        Attendance(200, "Kenneth", "Bonaagua", "Physics", "PHY101", "2024-03-29", "10:00 AM", "2023-2024", 1),
        Attendance(189, "Kenneth", "Bonaagua", "Chemistry", "CHEM101", "2024-03-29", "01:00 PM", "2023-2024", 1),
        Attendance(135, "Kenneth", "Bonaagua", "Biology", "BIO101", "2024-03-29", "03:00 PM", "2023-2024", 1),
        Attendance(85, "Kenneth", "Bonaagua", "History", "HIST101", "2024-03-29", "05:00 PM", "2023-2024", 1)
    )

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CustomDatePicker ()

            Spacer(Modifier.height(8.dp))
            AttendanceColumnName()
        }

        attendances.forEachIndexed { index, attendance ->
            item {
                AttendanceCard(attendance = attendance, index = index)
            }
        }
    }
}