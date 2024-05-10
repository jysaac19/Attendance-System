package attendanceappusers.adminapp.homescreen.attendancemanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.attendance.Attendance

@Composable
fun AttendanceCard(
    attendance: Attendance,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            // Display attendance information
            Text(text = "Name: ${attendance.firstname} ${attendance.lastname}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Subject: ${attendance.subjectName}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Date: ${attendance.date}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Time: ${attendance.time}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${attendance.status}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${attendance.usertype}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Delete button
                FloatingActionButton(
                    onClick = onDelete,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    // Icon for delete action
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
                // Update button
                FloatingActionButton(
                    onClick = onUpdate
                ) {
                    // Icon for update action
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}