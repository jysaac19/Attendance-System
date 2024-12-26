package attendanceappusers.studentapp.subjects.subjectattendances

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.AttendanceCard
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.AttendanceColumnNames
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.CustomDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun StudentSubjectAttendances (
    navController : NavController,
    viewModel: StudentSubjectAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val subjectInfo = SelectedSubjectHolder.getSelectedSubject()
    val currentYear = LocalDate.now().year
    val defaultEndDate = LocalDate.now()
    val defaultStartDate = LocalDate.of(currentYear, 1, 1)
    val attendances by viewModel.attendances.collectAsState()
    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }

    LaunchedEffect(startDate, endDate) {
        val startDateString = startDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) // Format start date
        val endDateString = endDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) // Format end date
        viewModel.filterStudentSubjectAttendancesByDateRange(startDateString, endDateString)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = subjectInfo?.name ?: "Attendances",
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

        AttendanceColumnNames()

        LazyColumn  (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(attendances) { index, attendance ->
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
}
