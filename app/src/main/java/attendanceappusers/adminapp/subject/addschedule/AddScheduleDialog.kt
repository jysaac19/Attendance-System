package attendanceappusers.adminapp.subject.addschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.screenuniversalcomponents.schedule.Clock
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleDialog(
    selectedDay: String,
    startTime: String,
    endTime: String,
    onSelectedDayChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onAddSchedule: (String, String, String) -> Unit,
    showDialog: Boolean,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = { onAddSchedule(selectedDay, startTime, endTime) }) {
                    Text("Confirm", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Dismiss", color = Color.White)
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val daysOfWeek = listOf(
                        "Weekdays",
                        "Monday",
                        "Tuesday",
                        "Wednesday",
                        "Thursday",
                        "Friday",
                        "Saturday"
                    )
                    UniversalDropDownMenu(
                        label = "Weekday",
                        items = daysOfWeek,
                        selectedItem = selectedDay,
                        onItemSelected = { onSelectedDayChange(it) }
                    )

                    // Start time selection
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { onStartTimeChange(it) },
                        label = { Text("Start Time") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        readOnly = true,
                        trailingIcon = {
                            Clock(
                                contentDescription = "Select Start Time",
                                onTimeSelected = { hours, minutes, amPm ->
                                    val formattedTime =
                                        String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                    onStartTimeChange(formattedTime)
                                }
                            )
                        }
                    )

                    // End time selection
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { onEndTimeChange(it) },
                        label = { Text("End Time") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        readOnly = true,
                        trailingIcon = {
                            Clock(
                                contentDescription = "Select End Time",
                                onTimeSelected = { hours, minutes, amPm ->
                                    val formattedTime =
                                        String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                    onEndTimeChange(formattedTime)
                                }
                            )
                        }
                    )
                }
            }
        )
    }
}