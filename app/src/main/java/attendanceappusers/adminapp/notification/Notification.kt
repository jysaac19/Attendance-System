package attendanceappusers.adminapp.notification

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider

@Composable
fun Notification(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val notifications = viewModel.notifications.collectAsState(initial = emptyList())

    LaunchedEffect(viewModel) {
        viewModel.getAllNotifications()
    }

    BackHandler {
        navController.popBackStack()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Notifications", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            notifications.value.forEach { notifications ->

                Card(

                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(

                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Column(
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = notifications.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = notifications.message, fontSize = 10.sp)
                    }

                }

            }

        }

    }
}