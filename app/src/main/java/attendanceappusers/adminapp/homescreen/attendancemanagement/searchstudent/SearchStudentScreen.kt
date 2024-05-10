package attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.user.SelectedStudent
import com.attendanceapp2.data.model.user.SelectedStudentHolder
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchStudentScreen(
    navController: NavController,
    viewModel: SearchStudentViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val students by viewModel.students.collectAsState()

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Select Student",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = { Text("Search") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { /* TODO: Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter User ID or Student Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(AdminHomeScreen.AttendanceManagement.name) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back to User Management"
                    )

                    Text(
                        text = "Back to User Management",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        LazyColumn {
            items(students) { student ->
                StudentListItems(student) {
                    SelectedStudentHolder.setSelectedStudent(
                        SelectedStudent(
                            student.id,
                            student.firstname,
                            student.lastname,
                            student.email,
                            student.password,
                            student.usertype,
                            student.department,
                            student.status
                        )
                    )
                    navController.navigate(AdminHomeScreen.SearchSubject.name)
                }
            }
        }
    }

    LaunchedEffect(searchText) {
        val query = searchText.text.trim()
        viewModel.searchStudents(query)
    }
}