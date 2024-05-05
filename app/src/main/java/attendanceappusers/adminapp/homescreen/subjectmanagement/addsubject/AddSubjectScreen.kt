package attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.attendanceapp2.R
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun AddSubjectScreen(
    navController: NavController,
    viewModel: AddSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var subjectCode by remember { mutableStateOf("") }
    var subjectName by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var selectedFaculty by remember { mutableStateOf("") }

    val rooms = listOf("RM401", "RM402", "RM403", "RM404", "RM405", "RM406", "RM407", "RM408", "RM409", "RM410", "RM411")

    val facultyList by viewModel.facultyList.collectAsState(initial = emptyList())

    val saveSubjectResult by viewModel.saveSubjectResult.collectAsState()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.nbslogo),
                contentDescription = "NBS Logo",
                modifier = Modifier.size(150.dp)
            )
        }

        item {
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
        }

        item {
            OutlinedTextField(
                value = subjectCode,
                onValueChange = { subjectCode = it.uppercase() },
                singleLine = true,
                label = { Text("Subject Code") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = subjectName,
                onValueChange = { subjectName = it.uppercase() }, // Convert to uppercase
                label = { Text("Subject Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }
        item {
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

        item {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
            ) {
                FloatingActionButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Cancel"
                        )

                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveSubject(
                                subjectCode,
                                subjectName,
                                selectedRoom,
                                selectedFaculty
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save"
                        )
                    }
                }
            }
        }

        item {
            val errorMessage = saveSubjectResult as? Results.AddSubjectResult
            Text(
                text = errorMessage?.failureMessage ?: "", // Display failure message if it exists
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val successMessage = saveSubjectResult as? Results.AddSubjectResult
            Text(
                text = successMessage?.successMessage ?: "", // Display success message if it exists
                color = Color.Green,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}