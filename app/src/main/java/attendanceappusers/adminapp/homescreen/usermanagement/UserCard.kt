package attendanceappusers.adminapp.homescreen.usermanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attendanceapp2.data.model.user.User

@Composable
fun UserCard(
    user: User,
    onDeleteClick: () -> Unit,
    onDeactivateClick: () -> Unit,
    onReactivateClick: () -> Unit,
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
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Name:",
                        fontSize = 12.sp
                    )
                    Text(
                        text = "User Type:",
                        fontSize = 12.sp
                    )
                }

                Column (
                    modifier = Modifier.weight(4f),
                ) {
                    Text(text = "${user.id}", fontSize = 12.sp)
                    Text(text = "${user.firstname} ${user.lastname}", fontSize = 12.sp)
                    Text(text = user.usertype, fontSize = 12.sp)
                }
            }


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
                                fontSize = 8.sp)
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
                if (user.status == "Active") {
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
                                    fontSize = 8.sp)
                            }
                        },
                        modifier = Modifier.weight(1f),
                    )
                } else {
                    FloatingActionButton(
                        onClick = onReactivateClick,
                        shape = RoundedCornerShape(10.dp),
                        contentColor = LocalContentColor.current,
                        content = {
                            Column (
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reactivate")
                                Text(text = "Reactivate",
                                    fontSize = 8.sp)
                            }
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
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
                                fontSize = 8.sp)
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}