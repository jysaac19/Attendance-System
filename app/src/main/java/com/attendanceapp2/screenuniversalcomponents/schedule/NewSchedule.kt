package com.attendanceapp2.screenuniversalcomponents.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attendanceapp2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSchedule(
    navController: NavController,
) {
    var schedules by remember { mutableStateOf(listOf<String>()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 100.dp),
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
                text = "Add New Schedule",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Here is where you could add a schedule to your subject",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display each schedule using ScheduleCard
            schedules.forEachIndexed { index, schedule ->
                Spacer(modifier = Modifier.height(8.dp))

                ScheduleCard(
                    subjectDay = "", // Pass the appropriate values
                    startTime = "",
                    endTime = "",
                    onDaySelected = { /* Handle day selection */ },
                    onStartTimeSelected = { /* Handle start time selection */ },
                    onEndTimeSelected = { /* Handle end time selection */ },
                    onCancel = {
                        // Remove the schedule at the current index
                        schedules = schedules.toMutableList().apply { removeAt(index) }
                    },
                    onSave = {
                        // Save the schedule
                        // You can implement the logic to save the schedule here
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Button to add a new schedule
            Button(
                onClick = {
                    // Add a new empty schedule to the list
                    schedules = schedules + ""
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .size(width = 350.dp, height = 50.dp),
            ) {
                Text(
                    text = "Add Schedule",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}