package attendanceappusers.adminapp.homescreen.addsubject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.attendanceapp2.R
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu

@Composable
fun AddSubjectScreen(
    navController: NavController,
    viewModel: AddSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var subjectCode by remember { mutableStateOf("") }
    var subjectName by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var selectedFaculty by remember { mutableStateOf("") }

    val rooms = listOf("RM401", "RM402", "RM403", "RM404", "RM405", "RM406", "RM407", "RM408", "RM409", "RM410", "RM411")

    val facultyList by viewModel.facultyList.collectAsState(initial = emptyList())


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.nbslogo),
            contentDescription = "NBS Logo",
            modifier = Modifier.size(150.dp)
        )

        Text(
            "Welcome to the Add Subject Screen",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            "Use this screen to add a new subject to the system.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = subjectCode,
            onValueChange = { subjectCode = it.uppercase() },
            singleLine = true,
            label = { Text("Subject Code") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = subjectName,
            onValueChange = { subjectName = it.uppercase() }, // Convert to uppercase
            label = { Text("Subject Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        UniversalDropDownMenu(
            label = "Room",
            items = rooms,
            selectedItem = selectedRoom,
            onItemSelected = { selectedRoom = it }
        )

        UniversalDropDownMenu(
            label = "Faculty",
            items = facultyList.map { "${it.firstname} ${it.lastname}" },
            selectedItem = selectedFaculty,
            onItemSelected = { selectedFaculty = it }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
        ) {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Cancel")
            }

            FloatingActionButton(
                onClick = { /* Handle Save action */ },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Save")
            }
        }
    }
}