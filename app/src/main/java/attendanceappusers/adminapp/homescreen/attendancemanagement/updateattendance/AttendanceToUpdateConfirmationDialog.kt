package attendanceappusers.adminapp.homescreen.attendancemanagement.updateattendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.StatusDropDownMenu
import com.attendanceapp2.data.model.attendance.Attendance

@Composable
fun AttendanceToUpdateConfirmationDialog(
    student: Attendance?,
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    showDialog: Boolean,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = title) },
            confirmButton = {
                Button(onClick = onConfirm) {
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
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (student != null) {
                        Text(
                            text = text,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Exposed dropdown menu
                    StatusDropDownMenu(
                        options = listOf("Present", "Absent", "Late"),
                        selectedStatus = selectedStatus,
                        onStatusSelected = onStatusSelected // Pass the function reference
                    )
                }
            }
        )
    }
}