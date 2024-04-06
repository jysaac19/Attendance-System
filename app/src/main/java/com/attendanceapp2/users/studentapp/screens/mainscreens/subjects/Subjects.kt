package com.attendanceapp2.users.studentapp.screens.mainscreens.subjects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.users.studentapp.screens.mainscreens.subjects.subjectscreencomponents.SubjectCard

@Composable
fun SubjectScreen ( navController : NavController) {
    val subjects = listOf(
        Subject(
            0,
            "MTH-101",
            "Mathematics",
            "RM409",
            "Jack Santos",
            "1:00 P.M.",
            "3:00 P.M.",
            "Tuesday",
            "2021-2022",
            1
        ),
        Subject(
            2,
            "SCI-101",
            "Science",
            "RM409",
            "Jack Santos",
            "3:30:00 P.M.",
            "5:30 P.M.",
            "Tuesday",
            "2021-2022",
            1
        ),
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(subjects) { subject ->
                SubjectCard(
                    subject,
                    onClick = {}
                )
            }
        }

    }
}