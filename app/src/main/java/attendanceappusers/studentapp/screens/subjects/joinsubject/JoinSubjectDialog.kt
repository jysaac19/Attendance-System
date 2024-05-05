package attendanceappusers.studentapp.screens.subjects.joinsubject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.Results

@Composable
fun JoinSubjectDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    joinSubjectResult: Results.JoinSubjectResult?,
    onJoinSubject: (String) -> Unit
) {
    if (showDialog) {
        var subjectCode by remember { mutableStateOf(TextFieldValue()) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Join Subject") },
            text = {
                Column {
                    OutlinedTextField(
                        value = subjectCode,
                        onValueChange = {
                            subjectCode = it
                        },
                        label = { Text("Join Code") },
                        shape = RoundedCornerShape(20.dp)
                    )
                    // Display success or failure message
                    joinSubjectResult?.let { result ->
                        result.successMessage?.let { message ->
                            Text(text = message, color = Color.Green)
                        }
                        result.failureMessage?.let { message ->
                            Text(text = message, color = Color.Red)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onJoinSubject(subjectCode.text)
                        onDismiss()
                    },
                ) {
                    Text("Join", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }
}