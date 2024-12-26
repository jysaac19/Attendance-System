package attendanceappusers.adminapp.attendance

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAttendanceList (
    navController: NavController,
    viewModel : AdminAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val currentYear = LocalDate.now().year
    val defaultEndDate = LocalDate.now()
    val defaultStartDate = LocalDate.of(currentYear, 1, 1)
    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }
    var selectedSubjectCode by remember { mutableStateOf("All") }
    val subjects by viewModel.subjects.collectAsState()
    val attendances by viewModel.attendances.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val sortedAttendances = attendances.sortedByDescending { attendance ->
        LocalDate.parse(attendance.date, formatter)
    }
    var query by remember { mutableStateOf("") }
    var selectedUserType by remember { mutableStateOf("All") }
    val userType = listOf("All", "Admin", "Student", "Faculty")
    var expanded by remember { mutableStateOf(false) }
    val syString = if (defaultEndDate.monthValue >= 9) {
        "${currentYear} - ${currentYear + 1}"
    } else {
        "${currentYear - 1} - ${currentYear}"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Attendances",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "S.Y. $syString",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                },
                label = { Text("Search") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { /* TODO: Handle search action */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text("Enter User ID or Name") }
            )

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

            UniversalDropDownMenu(
                label = "Subject",
                items = subjects,
                selectedItem = selectedSubjectCode,
                onItemSelected = {
                    selectedSubjectCode = it.split(" - ")[0]
                }
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = selectedUserType,
                    label = { Text("User Type") },
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userType.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedUserType = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

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

    LaunchedEffect(query, startDate, endDate, selectedSubjectCode, selectedUserType) {
        val startDateString = startDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        val endDateString = endDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))

        println("Query: $query")
        println("Start Date: $startDateString")
        println("End Date: $endDateString")
        println("Selected Subject Code: $selectedSubjectCode")
        println("Selected Status: $selectedUserType")

        viewModel.filterAttendancesByAdmin(
            searchQuery = query,
            subjectCode = selectedSubjectCode,
            usertype = selectedUserType,
            startDate = startDateString,
            endDate = endDateString
        )
    }
}