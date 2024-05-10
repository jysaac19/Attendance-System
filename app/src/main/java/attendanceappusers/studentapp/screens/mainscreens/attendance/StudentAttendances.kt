package attendanceappusers.studentapp.screens.mainscreens.attendance

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
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.AttendanceCard
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.AttendanceColumnName
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.CustomDatePicker
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun StudentAttendances (
    navController : NavController,
    viewModel : StudentAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    // Get the current year
    val currentYear = LocalDate.now().year

    // Default start date and end date
    val defaultEndDate = LocalDate.now()
    val defaultStartDate = LocalDate.of(currentYear, 1, 1) // January 1st of the current year

    // Collecting the start and end dates with default values
    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }

    var selectedSubjectCode by remember { mutableStateOf("All") }
    val subjects by viewModel.subjects.collectAsState()

    // Collect attendances and sort them by date in descending order (most recent first)
    val attendances by viewModel.attendances.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val sortedAttendances = attendances.sortedByDescending { attendance ->
        LocalDate.parse(attendance.date, formatter)
    }

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
                        selectedDate = startDate,
                        onDateSelected = { date ->
                            startDate = date
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomDatePicker(
                        label = "To",
                        selectedDate = endDate,
                        onDateSelected = { date ->
                            endDate = date
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        UniversalDropDownMenu(
            label = "Subject",
            items = subjects,
            selectedItem = selectedSubjectCode,
            onItemSelected = { selectedSubjectCode = it }
        )

        Spacer(Modifier.height(8.dp))

        AttendanceColumnName()

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            itemsIndexed(sortedAttendances) { index, attendance ->
                val backgroundColor = if (index % 2 == 0) Color.Transparent else Color.Gray
                AttendanceCard(
                    attendance = attendance,
                    backgroundColor = backgroundColor // Set your desired background color here
                )
            }
        }
    }

    LaunchedEffect(selectedSubjectCode, startDate, endDate) {
        val startDateString = startDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) // Format start date
        val endDateString = endDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) // Format end date

        println("Selected Subject: $selectedSubjectCode")
        println("Start Date: $startDateString")
        println("End Date: $endDateString")

        viewModel.filterStudentAttendances(
            subjectCode = selectedSubjectCode,
            startDate = startDateString,
            endDate = endDateString
        )
    }
}