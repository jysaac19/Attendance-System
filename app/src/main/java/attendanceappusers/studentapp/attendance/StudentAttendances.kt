package attendanceappusers.studentapp.attendance

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.AttendanceCard
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.AttendanceColumnNames
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.CustomDatePicker
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun StudentAttendances(
    navController: NavController,
    viewModel: StudentAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current
    val currentYear = LocalDate.now().year
    val defaultEndDate = LocalDate.now()
    val defaultStartDate = LocalDate.of(currentYear, 1, 1)
    val subjects by viewModel.subjects.collectAsState()
    val attendances by viewModel.attendances.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val sortedAttendances = attendances.sortedByDescending { attendance ->
        LocalDate.parse(attendance.date, formatter)
    }
    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }
    var selectedSubjectCode by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Attendances",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "S.Y. 2023 - 2024",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
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

        UniversalDropDownMenu(
            label = "Subject",
            items = subjects,
            selectedItem = selectedSubjectCode,
            onItemSelected = {
                selectedSubjectCode = it.split(" - ")[0]
            }
        )

        AttendanceColumnNames()

        LazyColumn  (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(sortedAttendances) { index, attendance ->
                val backgroundColor = if (index % 2 == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondaryContainer
                val contentColor = if (index % 2 == 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondary
                AttendanceCard(
                    attendance = attendance,
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        }
    }

    LaunchedEffect(selectedSubjectCode, startDate, endDate) {
        val startDateString = startDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        val endDateString = endDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))

        println("Selected Subject: $selectedSubjectCode")
        println("Start Date: $startDateString")
        println("End Date: $endDateString")

        viewModel.filterStudentAttendances(selectedSubjectCode, startDateString, endDateString)
    }
}