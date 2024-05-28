package com.attendanceapp2.screenuniversalcomponents.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleCard(
    subjectDay: String,
    startTime: String,
    endTime: String,
    onDaySelected: (String) -> Unit,
    onStartTimeSelected: (String) -> Unit,
    onEndTimeSelected: (String) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday").forEach { day ->
                    item {
                        RadioButtonOption(
                            text = day,
                            selectedOption = subjectDay,
                            onOptionSelected = { onDaySelected(it) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { onStartTimeSelected(it) },
                    label = { Text("Selected Start Time") },
                    trailingIcon = {
                        Clock(
                            contentDescription = "Start Time",
                            onTimeSelected = { hours, minutes, amPm ->
                                val formattedTime =
                                    String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                onStartTimeSelected(formattedTime)
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp),
                    shape = RoundedCornerShape(20.dp)
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = { onEndTimeSelected(it) },
                    label = { Text("End Time") },
                    trailingIcon = {
                        Clock(
                            contentDescription = "End Time",
                            onTimeSelected = { hours, minutes, amPm ->
                                val formattedTime =
                                    String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                onEndTimeSelected(formattedTime)
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onSave,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Save")
                }

                Button(
                    onClick = onCancel,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}



@Composable
fun RadioButtonOption(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = text == selectedOption,
            onClick = {
                onOptionSelected(text)
            }
        )
        Text(
            text = text,
            color = if (text == selectedOption) Color.Red else Color.Gray
        )
    }
}