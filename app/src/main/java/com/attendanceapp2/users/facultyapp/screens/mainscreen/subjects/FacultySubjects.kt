package com.attendanceapp2.users.facultyapp.screens.mainscreen.subjects

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.approutes.faculty.FacultySubjectsRoutes
import com.attendanceapp2.universaldata.SelectedSubject
import com.attendanceapp2.universaldata.SelectedSubjectHolder
import com.attendanceapp2.universalscreencomponents.subjectscreencomponents.SubjectCard
import com.attendanceapp2.universalviewmodel.SubjectViewModel
import com.attendanceapp2.users.facultyapp.screens.mainscreen.subjects.viewmodel.FacultySubjectAttendancesViewModel
import com.attendanceapp2.viewmodel.AppViewModelProvider

@Composable
fun FacultySubjects (
    navController : NavController,
    subjectVM: SubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
    facultySubjectAttendanceVM: FacultySubjectAttendancesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val subjects by subjectVM.subjects.collectAsState()

    LaunchedEffect(key1 = true) {
        subjectVM.fetchSubjectsForLoggedInUser()
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

        Spacer(modifier = Modifier.width(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(subjects) { subject ->
                SubjectCard(subject = subject) {
                    SelectedSubjectHolder.setSelectedSubject(
                        SelectedSubject(
                            id = subject.id,
                            code = subject.code,
                            name = subject.name,
                            room = subject.room,
                            faculty = subject.faculty,
                            day = subject.day,
                            start = subject.start,
                            end = subject.end
                        )
                    )
                    Log.d("SelectedSubject", "Selected subject: ${SelectedSubjectHolder.getSelectedSubject()}")
                    navController.navigate(FacultySubjectsRoutes.FacultySubjectAttendances.name)
                }
            }
        }
    }
}