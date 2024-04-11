package com.attendanceapp2.data.screen.subject

import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.attendanceapp2.R
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.capitalizeFirstLetter
import com.attendanceapp2.data.repositories.user.UserRepository
import com.attendanceapp2.viewmodel.AppViewModelProvider
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSubject (
    navController: NavController,
    viewModel: NewSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var subjectCode by remember { mutableStateOf("") }
    val newSubjectCode by remember { mutableStateOf("") }

    var subjectName by remember { mutableStateOf("") }

    //room dropdown menu
    val rooms = arrayOf("RM401", "RM402", "RM403", "RM404", "RM405", "RM406", "RM407", "RM408", "RM409", "RM410", "RM411", "RM412" )
    var roomsExpanded by remember { mutableStateOf(false) }
    var classroom by remember { mutableStateOf(rooms[0]) }

    //room dropdown menu
    val programs = arrayOf("BSCS", "BSA", "BSAIS", "BSE", "BSTM")
    var programsExpanded by remember { mutableStateOf(false) }
    var program by remember { mutableStateOf(programs[0]) }

    var searchFaculty by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .padding(24.dp),
                painter = painterResource(id = R.drawable.nbslogo),
                contentDescription = "NBS LOGO"
            )

            Text(
                text = "Creating New Subject",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Please fill all the necessary information of the subject",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = subjectCode,
                    onValueChange = { subjectCode = newSubjectCode.replace(" ", "").toUpperCase() },
                    label = { Text("Subject Code") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = subjectName,
                    onValueChange = { subjectName = capitalizeFirstLetter(it.toLowerCase()) },
                    label = { Text("Subject Name") },
                    modifier = Modifier.weight(2f),
                    singleLine = false,
                    shape = RoundedCornerShape(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = roomsExpanded,
                        onExpandedChange = {
                            roomsExpanded = !roomsExpanded
                        }
                    ) {
                        OutlinedTextField(
                            value = classroom,
                            onValueChange = { },
                            label = { Text("Room") },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = roomsExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center)
                        )

                        ExposedDropdownMenu(
                            expanded = roomsExpanded,
                            onDismissRequest = { roomsExpanded = false }
                        ) {
                            rooms.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        classroom = item
                                        roomsExpanded = false
                                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = programsExpanded,
                        onExpandedChange = {
                            programsExpanded = !programsExpanded
                        }
                    ) {
                        OutlinedTextField(
                            value = program,
                            onValueChange = { },
                            label = { Text("Course") },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = programsExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center)
                        )

                        ExposedDropdownMenu(
                            expanded = programsExpanded,
                            onDismissRequest = { programsExpanded = false }
                        ) {
                            programs.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        program = item
                                        programsExpanded = false
                                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {  },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .size(width = 350.dp,height = 50.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create Subject",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        imageVector = Icons.Default.Subject,
                        contentDescription = "Subject",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }


//            OutlinedTextField(
//                value = searchFaculty,
//                onValueChange = { searchFaculty = it },
//                label = { Text("Assign Faculty") },
//                trailingIcon = {
//                    Button(
//                        onClick = {  },
//                        shape = RoundedCornerShape(20.dp),
//                        modifier = Modifier
//                            .size(width = 100.dp, height = 50.dp)
//                            .padding(vertical = 4.dp, horizontal = 8.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Search",
//                            tint = Color.White
//                        )
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                singleLine = true,
//                shape = RoundedCornerShape(20.dp)
//            )
        }
    }
}