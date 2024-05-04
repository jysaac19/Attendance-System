package com.attendanceapp2.screenuniversalcomponents.subjectuicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attendanceapp2.data.model.Subject

@Composable
fun SubjectCard(subject: Subject, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        onClick = { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = subject.name,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                maxLines = 1, // Limit to 2 lines
                overflow = TextOverflow.Ellipsis // Add ellipsis for overflow
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Prof. ${subject.faculty}",
                fontSize = 12.sp
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                Color.Red.copy(alpha = 0.8f)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
//                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = subject.code,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}