package attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.attendanceapp2.data.model.user.SelectedStudent

@Composable
fun AttendanceStatusConfirmationDialog(
    student: SelectedStudent?,
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    showDialog: Boolean,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = title) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Confirm", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Dismiss", color = Color.White)
                }
            },
            text = {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (student != null) {
                        Text(
                            text = text,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Exposed dropdown menu
                    StatusDropDownMenu(
                        options = listOf("Present", "Absent", "Late"),
                        selectedStatus = selectedStatus,
                        onStatusSelected = onStatusSelected // Pass the function reference
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDropDownMenu(
    options: List<String>,
    selectedStatus: String,
    onStatusSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedStatus,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Select Status") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            shape = RoundedCornerShape(20.dp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                focusedLabelColor = Color.Gray,
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, status ->
                DropdownMenuItem(
                    text = { Text(text = status) },
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
//                        Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}