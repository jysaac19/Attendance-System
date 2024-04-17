package com.attendanceapp2.user.studentapp.screens.subjects


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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.universal.data.SelectedSubjectHolder
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.AttendanceCard
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.AttendanceColumnName
import com.attendanceapp2.universal.screencomponents.attendancescreencomponents.CustomDatePicker
import com.attendanceapp2.user.studentapp.viewmodel.StudentSubjectAttendanceViewModel
import java.time.LocalDate

@Composable
fun StudentSubjectAttendances (
    navController : NavController,
    viewModel: StudentSubjectAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    LaunchedEffect(true) {
        viewModel.fetchStudentSubjectAttendances()
    }

    val subjectInfo = SelectedSubjectHolder.getSelectedSubject()

    // Collecting the start and end dates
    val endDate by viewModel.endDate.collectAsState()
    val startDate by viewModel.startDate.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = subjectInfo?.name ?: "Attendances",
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
                        selectedDate = startDate ?: LocalDate.now(),
                        onDateSelected = { date ->
                            viewModel.setStartDate(date)
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomDatePicker(
                        label = "To",
                        selectedDate = endDate ?: LocalDate.now(),
                        onDateSelected = { date ->
                            viewModel.setEndDate(date)
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        AttendanceColumnName()

        LazyColumn {
            itemsIndexed(viewModel.studentSubjectAttendances.value) { index, attendance ->
                val backgroundColor = if (index % 2 == 0) Color.White else Color.Gray
                AttendanceCard(
                    attendance = attendance,
                    backgroundColor = backgroundColor // Set your desired background color here
                )
            }
        }
    }
}
