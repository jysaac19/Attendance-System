package com.attendanceapp2.users.facultyapp.screens.mainscreen.attendances

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.screenuniversalcomponents.attendancescreencomponents.AttendanceCard
import com.attendanceapp2.screenuniversalcomponents.attendancescreencomponents.AttendanceColumnName
import com.attendanceapp2.screenuniversalcomponents.attendancescreencomponents.CustomDatePicker
import com.attendanceapp2.screenuniversalcomponents.attendancescreencomponents.SubjectDropdown
import java.time.LocalDate

@Composable
fun FacultyAttendances (navController : NavController) {
    val attendances = listOf(
        Attendance(235, "Kenneth", "Bonaagua", "Mathematics", "MATH101", "2024-03-29", "08:00 AM"),
        Attendance(200, "Kenneth", "Bonaagua", "Physics", "PHY101", "2024-03-29", "10:00 AM"),
        Attendance(189, "Kenneth", "Bonaagua", "Chemistry", "CHEM101", "2024-03-29", "01:00 PM"),
        Attendance(135, "Kenneth", "Bonaagua", "Biology", "BIO101", "2024-03-29", "03:00 PM"),
        Attendance(85, "Kenneth", "Bonaagua", "History", "HIST101", "2024-03-29", "05:00 PM")
    )

    var startdate by remember { mutableStateOf(LocalDate.now()) }
    var enddate by remember { mutableStateOf(LocalDate.now()) }
    var selectedSubject by remember { mutableIntStateOf(0) }
    val subjects = listOf("All", "Math", "Science", "History", "English", "Art")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Attendances",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "S.Y. 2023 - 2024",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomDatePicker(
                        label = "From",
                        selectedDate = startdate,
                        onDateSelected = { date ->
                            startdate = date
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomDatePicker(
                        label = "To",
                        selectedDate = enddate,
                        onDateSelected = { date ->
                            enddate = date
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        SubjectDropdown(
            label = "Subjects",
            items = subjects,
            selectedItem = selectedSubject,
            onItemSelected = { selectedSubject = it }
        )

        Spacer(Modifier.height(8.dp))

        Spacer(Modifier.height(8.dp))

        AttendanceColumnName()

        LazyColumn {
            attendances.forEachIndexed { index, attendance ->
                item {
                    AttendanceCard(attendance = attendance, index = index)
                }
            }
        }
    }
}