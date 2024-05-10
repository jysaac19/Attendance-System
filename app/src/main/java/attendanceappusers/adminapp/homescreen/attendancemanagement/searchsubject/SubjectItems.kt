package attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.subject.Subject

@Composable
fun SubjectListItems(
    subject: Subject,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "User ID: ${subject.id}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Name: ${subject.code} - ${subject.name}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Department: ${subject.faculty}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Department: ${subject.room}")
        }
    }
}