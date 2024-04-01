package com.attendanceapp2.screens.mainscreens.attendances.attendancescreencomponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    label: String,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val date = remember { mutableStateOf(selectedDate)}
    val isOpen = remember { mutableStateOf(false)}

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            readOnly = true,
            value = date.value.format(DateTimeFormatter.ISO_DATE),
            label = { Text(label) },
            onValueChange = {},
            trailingIcon = {
                IconButton(
                    onClick = { isOpen.value = true } // show the dialog
                ) {
                    Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar")
                }
            },
            shape = RoundedCornerShape(20.dp), // Set the corner radius to 20.dp
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color.Red, // Remove focus border color
                focusedLabelColor = Color.Gray,
            )
        )
    }

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false // close dialog

                if (it != null) { // Set the date
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                    onDateSelected(date.value)
                }
            },
            onCancel = {
                isOpen.value = false //close dialog
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text(
                    text = "Accept",
                    color = Color.White
                )
            }
        },

        dismissButton = {
            Button(onClick = onCancel) {
                Text(
                    text = "Cancel",
                    color = Color.White
                )
            }
        }
    ) {
        DatePicker(state = state)
    }
}