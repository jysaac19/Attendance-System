package attendanceappusers.adminapp.homescreen

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
            onClick = { navController.navigate(AdminHomeScreen.AddSubject.name) },
            modifier = Modifier
                .size(300.dp, 120.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    "Create Subject",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Navigate to Add Subject screen\nAdd new subjects to the system",
                    fontSize = 12.sp,
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Card(
            onClick = { navController.navigate( AdminHomeScreen.UserManagement.name ) },
            modifier = Modifier
                .size(300.dp, 120.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    "User Management",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Navigate to Manage Users screen\nManage existing users in the system",
                    fontSize = 12.sp,
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Card(
            onClick = { navController.navigate( AdminHomeScreen.AttendanceManagement.name) },
            modifier = Modifier
                .size(300.dp, 120.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    "Attendance Management",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Navigate to Attendance Management screen\nManage attendance records",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Card(
            onClick = { navController.navigate(AdminHomeScreen.SubjectManagement.name) },
            modifier = Modifier
                .size(300.dp, 120.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    "Subject Management",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Navigate to Subject Management screen\nManage subjects",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}