package attendanceappusers.adminapp.subject.adminsubject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.StatusDropDownMenu

@Composable
fun DownloadAttendanceDialog(
    onDismiss: () -> Unit,
    onDownload: (String) -> Unit,
    showDialog: Boolean,
    options: List<String>
) {
    var selectedPeriod by remember { mutableStateOf(options.firstOrNull() ?: "") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Download Attendance") },
            confirmButton = {
                Button(onClick = { onDownload(selectedPeriod) }) {
                    Text("Download", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel", color = Color.White)
                }
            },
            text = {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Select the period for which you want to download the attendance:",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatusDropDownMenu(
                        options = options,
                        selectedStatus = selectedPeriod,
                        onStatusSelected = { selectedPeriod = it }
                    )
                }
            }
        )
    }
}