package attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.user.User

@Composable
fun StudentListItems(
    user: User,
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
            Text(text = "User ID: ${user.id}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Name: ${user.firstname} ${user.lastname}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Department: ${user.department}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Department: ${user.status}")
        }
    }
}