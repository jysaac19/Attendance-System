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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.weight(1.5f),
                ) {
                    Text(text = "Name:", fontSize = 12.sp)
                    Text(text = "Subject:", fontSize = 12.sp)
                    Text(text = "Date:", fontSize = 12.sp)
                    Text(text = "Time:", fontSize = 12.sp)
                    Text(text = "Status:", fontSize = 12.sp)
                }

                Column (
                    modifier = Modifier.weight(4f),
                ) {
                    Text(text = "${attendance.firstname} ${attendance.lastname}", fontSize = 10.sp)
                    Text(text = attendance.subjectName, fontSize = 10.sp)
                    Text(text = attendance.date, fontSize = 10.sp)
                    Text(text = attendance.time, fontSize = 10.sp)
                    Text(text = attendance.status, fontSize = 10.sp)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                FloatingActionButton(
                    onClick = onDelete,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }

                FloatingActionButton(
                    onClick = onUpdate
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}