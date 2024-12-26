package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject.UpdateSubjectViewModel
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
    viewModel: SubjectManagementViewModel = viewModel(factory = AppViewModelProvider.Factory),
    updateSubjectViewModel: UpdateSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    val activeSubjects by viewModel.activeSubjects.collectAsState()
    val archivedSubjects by viewModel.archivedSubjects.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showArchiveDialog by remember { mutableStateOf(false) }
    var showUnarchiveDialog by remember { mutableStateOf(false) }
    var showRemoveFacultyDialog by remember { mutableStateOf(false) }

    var subjectToDelete by remember { mutableStateOf<Subject?>(null) }
    var subjectToArchive by remember { mutableStateOf<Subject?>(null) }
    var subjectToUnarchive by remember { mutableStateOf<Subject?>(null) }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val mainSubjectList = if (selectedTabIndex == 0) activeSubjects else archivedSubjects

    LaunchedEffect(searchText) {
        viewModel.searchSubjectsByCode(searchText)
    }

    LaunchedEffect(Unit) {
        viewModel.updateSubjectManagementList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            "Subject Management",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it.uppercase() },
            label = { Text("Search") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { /* TODO: Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter Subject Code or Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(AdminHomeScreen.HomeScreen.name) },
                modifier = Modifier
                    .padding(end = 2.dp, top = 4.dp, bottom = 4.dp)
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back to Home"
                    )

                    Text(
                        text = "Back to Home",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Button(
                onClick = { navController.navigate(AdminHomeScreen.AddSubject.name) },
                modifier = Modifier
                    .padding(end = 2.dp, top = 4.dp, bottom = 4.dp)
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Add New Subject",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Icon(Icons.Default.Add, contentDescription = "Go To Add New Subject Screen")
                }
            }
        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp))
            ) {
                Text(text = "Active")
            }
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .clip(RoundedCornerShape(topEnd = 10.dp))
            ) {
                Text(text = "Archived")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        mainSubjectList.let { subjects ->
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
                                    subject.facultyName,
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
                                        subject.facultyName,
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
                                        subject.facultyName,
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
                        },
                        onUnarchiveClick = {
                            subjectToUnarchive = subject
                            showUnarchiveDialog = true
                        },
                        onRemoveFacultyClick = {
                            SelectedSubjectHolder.setSelectedSubject(
                                SelectedSubject(
                                    subject.id,
                                    subject.code,
                                    subject.name,
                                    subject.room,
                                    subject.facultyName,
                                    subject.subjectStatus,
                                    subject.joinCode
                                )
                            )
                            showRemoveFacultyDialog = true
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
        },
        onDismiss = {
            showArchiveDialog = false
            subjectToArchive = null
        },
        showDialog = showArchiveDialog
    )

    ConfirmDialog(
        title = "Unarchive Confirmation",
        message = "Are you sure you want to unarchive this subject?",
        onConfirm = {
            subjectToUnarchive?.let { subject ->
                viewModel.unarchiveSubject(subject)
                showUnarchiveDialog = false
                subjectToUnarchive = null
            }
        },
        onDismiss = {
            showUnarchiveDialog = false
            subjectToUnarchive = null
        },
        showDialog = showUnarchiveDialog
    )

    ConfirmDialog(
        title = "Remove Faculty Confirmation",
        message = "Are you sure you want to remove ${SelectedSubjectHolder.getSelectedSubject()?.faculty} as faculty from this subject?",
        onConfirm = {
            coroutineScope.launch {
                val subject = SelectedSubjectHolder.getSelectedSubject()
                if (subject != null) {
                    updateSubjectViewModel.removeFaculty(
                        Subject(
                            subject.id,
                            subject.code,
                            subject.name,
                            subject.room,
                            subject.faculty,
                            subject.subjectStatus,
                            subject.joinCode
                        )
                    )
                }
                viewModel.updateSubjectManagementList()
                showRemoveFacultyDialog = false
            }
        },
        onDismiss = {
            showRemoveFacultyDialog = false
        },
        showDialog = showRemoveFacultyDialog
    )
}