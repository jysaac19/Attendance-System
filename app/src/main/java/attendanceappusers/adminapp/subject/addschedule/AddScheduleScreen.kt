package attendanceappusers.adminapp.subject.addschedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.screen.schedule.Clock
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen (
    navController: NavController,
    viewModel: AddScheduleViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val subjectInfo = SelectedSubjectHolder.getSelectedSubject()
    val schedules = remember { mutableStateListOf<Schedule>() }

    // Variable to track the number of cards to display
    var cardCount by remember { mutableIntStateOf(1) }

    // Function to add a new empty schedule
    fun addSchedule() {
        coroutineScope.launch {
            if (subjectInfo!= null) {
                schedules.add(
                    Schedule(
                    subjectId = subjectInfo.id,
                    subjectCode = subjectInfo.code,
                    subjectName = subjectInfo.name,
                    day = "",
                    start = "",
                    end = ""
                )
                ) // Add an empty schedule
                cardCount++ // Increment the card count
            }
        }
    }

    // Function to remove a schedule at a specific index
    fun removeSchedule(index: Int) {
        schedules.removeAt(index) // Remove the schedule at the given index
        cardCount-- // Decrement the card count
    }

    LazyColumn {
        items(cardCount) { index ->
            val schedule = schedules.getOrNull(index)

            if (schedule != null) {
                var selectedDay by remember { mutableStateOf(schedule.day) }
                var startTime by remember { mutableStateOf(schedule.start) }
                var endTime by remember { mutableStateOf(schedule.end) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Weekday:")
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val daysOfWeek = listOf(
                                "Monday",
                                "Tuesday",
                                "Wednesday",
                                "Thursday",
                                "Friday",
                                "Saturday"
                            )
                            items(daysOfWeek) { day ->
                                RadioButton(
                                    selected = selectedDay == day,
                                    onClick = { selectedDay = day },
                                    enabled = true,
                                )
                                Text(day)
                            }
                        }

                        // Start time selection
                        OutlinedTextField(
                            value = startTime,
                            onValueChange = { startTime = it },
                            label = { Text("Start Time") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            trailingIcon = {
                                Clock(
                                    contentDescription = "Select Start Time",
                                    onTimeSelected = { hours, minutes, amPm ->
                                        // Update the start time with the selected time
                                        val formattedTime =
                                            String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                        startTime = formattedTime
                                    }
                                )
                            }
                        )

                        // End time selection
                        OutlinedTextField(
                            value = endTime,
                            onValueChange = { endTime = it },
                            label = { Text("End Time") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            trailingIcon = {
                                Clock(
                                    contentDescription = "Select End Time",
                                    onTimeSelected = { hours, minutes, amPm ->
                                        // Update the start time with the selected time
                                        val formattedTime =
                                            String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                        endTime = formattedTime
                                    }
                                )
                            }
                        )

                        // Remove button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            FloatingActionButton(
                                onClick = { removeSchedule(index) },
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Remove")
                            }


                            FloatingActionButton(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = { addSchedule() },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(300.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add New Schedule",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}