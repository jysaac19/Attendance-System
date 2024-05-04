package attendanceappusers.adminapp.homescreen.addattendance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAttendanceScreen(
    navController: NavController,
    viewModel: AddAttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var selectedUsers by remember { mutableStateOf<List<User>>(emptyList()) }
    var searchResults by remember { mutableStateOf<List<User>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        // Search bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it}, //searchResults = viewModel.searchUsers(it) },
            label = { Text("Search by userId") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { /* Optional: Add a clear functionality if needed */ }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear")
                }
            }
        )

        // Display search results
        LazyColumn {
            items(searchResults) { user ->
                Row(
                    modifier = Modifier
                        .clickable {
                            // Select the user and add to the list of selected users
                            selectedUsers = selectedUsers + user
                        }
                        .padding(16.dp)
                ) {
                    // Display user information here, e.g., user name, email, etc.
                    // Optionally, you can include a checkbox to indicate selection
                }
            }
        }

        // Display selected users if needed

        // Continue button
        Button(onClick = {
            // Pass selected users to the appropriate destination or perform further actions
            navController.navigate("destination", /* pass selectedUsers*/)
        }) {
            Text("Continue")
        }
    }
}