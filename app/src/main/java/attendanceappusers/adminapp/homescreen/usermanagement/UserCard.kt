package attendanceappusers.adminapp.homescreen.usermanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attendanceapp2.data.model.User

@Composable
fun UserCard(
    user: User,
    onDeleteClick: () -> Unit,
    onDeactivateClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "User ID: ${user.id}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Name: ${user.firstname} ${user.lastname}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "User Type: ${user.usertype}")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = onDeleteClick,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = LocalContentColor.current,
                    content = {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                            Text(text = "Delete",
                                fontSize = 10.sp)
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
                FloatingActionButton(
                    onClick = onDeactivateClick,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = LocalContentColor.current,
                    content = {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.DoNotDisturb, contentDescription = "Deactivate")
                            Text(text = "Deactivate",
                                fontSize = 10.sp)
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
                FloatingActionButton(
                    onClick = onUpdateClick,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = LocalContentColor.current,
                    content = {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Update, contentDescription = "Update")
                            Text(text = "Update",
                                fontSize = 10.sp)
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}