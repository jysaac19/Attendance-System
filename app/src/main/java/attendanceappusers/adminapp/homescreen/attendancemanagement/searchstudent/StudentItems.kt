package attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.weight(1.5f),
                ) {
                    Text(
                        text = "User ID:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Name:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Department:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Status:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Column (
                    modifier = Modifier.weight(3f),
                ) {
                    Text(text = "${user.id}", fontSize = 10.sp)
                    Text(text = "${user.firstname} ${user.lastname}", fontSize = 10.sp)
                    Text(text = user.department, fontSize = 10.sp)
                    Text(text = user.status, fontSize = 10.sp)
                }
            }
        }
    }
}