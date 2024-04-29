package attendanceappusers.adminapp.homescreen.adduser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attendanceapp2.R
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu

@Composable
fun AddUserScreen(
    navController: NavController
) {
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("BSCS") }
    var selectedFaculty by remember { mutableStateOf("Student") }

    val department = listOf("BSCS", "BSA", "BSAIS", "BSE", "BSTM")
    val usertype = listOf("Student", "Faculty", "Admin")

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
            "Welcome to the Add User Screen",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            "Use this screen to add a new user to the system.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname = it },
            singleLine = true,
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname = it },
            singleLine = true,
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        UniversalDropDownMenu(
            label = "Department",
            items = department,
            selectedItem = selectedRoom,
            onItemSelected = { selectedRoom = it }
        )

        UniversalDropDownMenu(
            label = "User Type",
            items = usertype,
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
                    .weight(1f),
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Cancel")
            }

            FloatingActionButton(
                onClick = { /* Handle Save action */ },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Save")
            }
        }
    }
}