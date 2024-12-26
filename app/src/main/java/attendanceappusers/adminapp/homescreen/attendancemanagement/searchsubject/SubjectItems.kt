package attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attendanceapp2.data.model.subject.Subject

@Composable
fun SubjectListItems(
    subject: Subject,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = "Subject ID:", fontSize = 12.sp)
                    Text(text = "Subject Name:", fontSize = 12.sp)
                    Text(text = "Faculty:", fontSize = 12.sp)
                    Text(text = "Room:", fontSize = 12.sp)
                }

                Column (
                    modifier = Modifier.weight(2f),
                ) {
                    Text(text = "${subject.id}", fontSize = 10.sp)
                    Text(text = "${subject.code} - ${subject.name}", fontSize = 10.sp)
                    Text(text = subject.facultyName, fontSize = 10.sp)
                    Text(text = subject.room, fontSize = 10.sp)
                }
            }
        }
    }
}