package attendanceappusers.adminapp.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Card
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = { navController.navigate(AdminHomeScreen.AddUser.name) },
            modifier = Modifier
                .size(200.dp, 120.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Add User",
                    fontSize = 24.sp,
                    color = Color.White
                )
                Text(
                    "Navigate to Add User screen\nAdd new users to the system",
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Card(
            onClick = { navController.navigate(AdminHomeScreen.AddSubject.name) },
            modifier = Modifier
                .size(200.dp, 120.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Add Subject",
                    fontSize = 24.sp,
                    color = Color.White
                )
                Text(
                    "Navigate to Add Subject screen\nAdd new subjects to the system",
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}