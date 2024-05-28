package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Update
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
import com.attendanceapp2.data.model.subject.Subject

@Composable
fun SubjectItem(
    subject: Subject,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onUnarchiveClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Subject: ${subject.name}", fontWeight = FontWeight.Bold)
            Text(text = "Code: ${subject.code}")
            Text(text = "Room: ${subject.room}")
            Text(text = "Faculty: ${subject.facultyName}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FloatingActionButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f),
                ) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Subject"
                        )
                        Text(
                            text = "Delete",
                            fontSize = 10.sp
                        )
                    }
                }

                FloatingActionButton(
                    onClick = onUpdateClick,
                    modifier = Modifier.weight(1f),
                ) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Update,
                            contentDescription = "Update Subject"
                        )
                        Text(
                            text = "Update",
                            fontSize = 10.sp
                        )
                    }
                }

                if (subject.subjectStatus == "Active") {
                    FloatingActionButton(
                        onClick = onArchiveClick,
                        modifier = Modifier.weight(1f),
                    ) {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Archive,
                                contentDescription = "Archive Subject"
                            )
                            Text(
                                text = "Archive",
                                fontSize = 10.sp
                            )
                        }
                    }
                } else {
                    FloatingActionButton(
                        onClick = onUnarchiveClick,
                        modifier = Modifier.weight(1f),
                    ) {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Archive,
                                contentDescription = "Unarchive Subject"
                            )
                            Text(
                                text = "Unarchive",
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}