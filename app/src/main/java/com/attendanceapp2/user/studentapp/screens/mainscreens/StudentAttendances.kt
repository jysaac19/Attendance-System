package com.attendanceapp2.user.studentapp.screens.mainscreens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.AttendanceCard
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.AttendanceColumnName
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.CustomDatePicker
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.SubjectDropdown
import com.attendanceapp2.user.studentapp.viewmodel.StudentAttendanceViewModel
import java.time.LocalDate

@Composable
fun StudentAttendances (
    userId: Long,
    navController : NavController,
    viewModel : StudentAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    var startdate by remember { mutableStateOf(LocalDate.now()) }
    var enddate by remember { mutableStateOf(LocalDate.now()) }
    var selectedSubject by remember { mutableStateOf("") }
    val subjects = listOf("All", "MATH301", "CS101", "ENG201", "PHY401", "CHEM501", "BIO601", "HIST701")
    val attendanceList = viewModel.getAttendancesByLoggedInUser(userId).collectAsState(initial = emptyList())
    val filterAttendance = viewModel.filterAttendance(startdate.toString(),enddate.toString(), userId,selectedSubject).collectAsState(initial = emptyList())

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

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            "S.Y. 2023 - 2024",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

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

        AttendanceColumnName()

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            filterAttendance.value.forEachIndexed { index, attendance ->
                val backgroundColor = if (index % 2 == 0) Color.Transparent else Color.Gray
                item {
                    AttendanceCard(attendance = attendance, backgroundColor = backgroundColor)
                }
            }
        }
    }
}