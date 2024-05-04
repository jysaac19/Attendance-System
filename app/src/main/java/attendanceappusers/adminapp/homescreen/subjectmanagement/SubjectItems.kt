package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.Subject

@Composable
fun SubjectItem(subject: Subject, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Subject: ${subject.name}", fontWeight = FontWeight.Bold)
            Text(text = "Code: ${subject.code}")
            Text(text = "Room: ${subject.room}")
            Text(text = "Faculty: ${subject.faculty}")
        }
    }
}