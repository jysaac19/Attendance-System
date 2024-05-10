package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UpdateSubject
import com.attendanceapp2.data.model.subject.UpdatingSubjectHolder
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.navigation.approutes.admin.AdminSubject
import kotlinx.coroutines.launch

@Composable
fun SubjectManagementScreen (
    navController: NavController,
    viewModel: SubjectManagementViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val subjectList by viewModel.subjects.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showArchiveDialog by remember { mutableStateOf(false) }

    var subjectToDelete by remember { mutableStateOf<Subject?>(null) }
    var subjectToArchive by remember { mutableStateOf<Subject?>(null) }

    // Launch search when searchText changes
    LaunchedEffect(searchText.text) {
        viewModel.searchSubjectsByCode(searchText.text)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Subject Management",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
            placeholder = { Text("Enter Subject Code") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(AdminHomeScreen.HomeScreen.name) },
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
                        contentDescription = "Back to Home"
                    )

                    Text(
                        text = "Back to Home",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate(AdminHomeScreen.AddSubject.name) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add New Subject",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Icon(Icons.Default.Add, contentDescription = "Go To Add New Subject Screen")
                }
            }
        }

        // Display the list of subjects
        subjectList.let { subjects ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(subjects) { subject ->
                    SubjectItem(
                        subject = subject,
                        onClick = {
                            coroutineScope.launch {
                                // Convert Subject to SelectedSubject
                                val selectedSubject = SelectedSubject(
                                    subject.id,
                                    subject.code,
                                    subject.name,
                                    subject.room,
                                    subject.faculty,
                                    subject.subjectStatus,
                                    subject.joinCode
                                )
                                // Set the selected subject
                                SelectedSubjectHolder.setSelectedSubject(selectedSubject)
                                // Navigate to the subject screen
                                navController.navigate(AdminSubject.SubjectScreen.name)
                            }
                        },
                        onDeleteClick = {
                            subjectToDelete = subject
                            showDeleteDialog = true
                        },
                        onUpdateClick = {
                            coroutineScope.launch {
                                SelectedSubjectHolder.setSelectedSubject(
                                    SelectedSubject(
                                        subject.id,
                                        subject.code,
                                        subject.name,
                                        subject.room,
                                        subject.faculty,
                                        subject.subjectStatus,
                                        subject.joinCode
                                    )
                                )

                                UpdatingSubjectHolder.setSelectedSubject(
                                    UpdateSubject(
                                        subject.id,
                                        subject.code,
                                        subject.name,
                                        subject.room,
                                        subject.faculty,
                                        subject.subjectStatus,
                                        subject.joinCode
                                    )
                                )
                                navController.navigate(AdminHomeScreen.UpdateSubject.name)
                            }

                        },
                        onArchiveClick = {
                            subjectToArchive = subject
                            showArchiveDialog = true
                        }
                    )
                }
            }
        }
    }

    ConfirmDialog(
        title = "Delete Confirmation",
        message = "Are you sure you want to delete this subject?",
        onConfirm = {
            subjectToDelete?.let { subject ->
                viewModel.deleteSubject(subject)
                showDeleteDialog = false
                subjectToDelete = null
            }
            viewModel.getAllSubjects()
        },
        onDismiss = {
            showDeleteDialog = false
            subjectToDelete = null
        },
        showDialog = showDeleteDialog
    )

    ConfirmDialog(
        title = "Archive Confirmation",
        message = "Are you sure you want to archive this subject?",
        onConfirm = {
            subjectToArchive?.let { subject ->
                viewModel.archiveSubject(subject)
                showArchiveDialog = false
                subjectToArchive = null
            }
            viewModel.getAllSubjects()
        },
        onDismiss = {
            showArchiveDialog = false
            subjectToArchive = null
        },
        showDialog = showArchiveDialog
    )
}