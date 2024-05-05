package attendanceappusers.studentapp.screens.mainscreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.studentapp.screens.subjects.joinsubject.JoinSubjectDialog
import attendanceappusers.studentapp.screens.subjects.joinsubject.JoinSubjectViewModel
import com.attendanceapp2.navigation.approutes.student.StudentSubjectsRoutes
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.LoggedInUserHolder
import com.attendanceapp2.data.model.SelectedSubject
import com.attendanceapp2.data.model.SelectedSubjectHolder
import com.attendanceapp2.screenuniversalcomponents.subjectuicomponents.SubjectCard
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.data.model.Results
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("LogNotTimber")
@Composable
fun StudentSubjects (
    navController : NavController,
    viewModel: SubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    joinViewModel: JoinSubjectViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val loggedInUser = LoggedInUserHolder.getLoggedInUser()

    val subjects by viewModel.subjects.collectAsState()

    // Remember mutable state variable for joinSubjectResult
    var joinSubjectResult by remember { mutableStateOf<Results.JoinSubjectResult?>(null) }

    LaunchedEffect(loggedInUser) {
        viewModel.fetchSubjectsForLoggedInUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Subjects",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            "S.Y. 2023 - 2024",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Join Subject"
                    )

                    Text(
                        text = "Join Subject",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(subjects) { subject ->
                SubjectCard(subject = subject) {
                    SelectedSubjectHolder.setSelectedSubject(
                        SelectedSubject(
                            subject.id,
                            subject.code,
                            subject.name,
                            subject.room,
                            subject.faculty,
                            subject.joinCode
                        )
                    )
                    Log.d("SelectedSubject", "Selected subject: ${SelectedSubjectHolder.getSelectedSubject()}")
                    navController.navigate(StudentSubjectsRoutes.StudentSubjectAttendances.name)
                }
            }
        }

        JoinSubjectDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            joinSubjectResult = joinSubjectResult,
            onJoinSubject = { subjectCode ->
                coroutineScope.launch {
                    // Call join subject function in the view model
                    loggedInUser?.let { user ->
                        joinSubjectResult = joinViewModel.joinSubjectByCode(user.userId, subjectCode)
                    }
                    viewModel.fetchSubjectsForLoggedInUser()
                    showDialog = false
                }
            }
        )
    }
}