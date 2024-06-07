package attendanceappusers.notification

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.notification.NotificationViewModel
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.user.LoggedInUserHolder

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
                        .padding(vertical = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Column(
                        modifier = modifier.padding(10.dp)
                    ) {
                        Text(text = notifications.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(text = notifications.message, fontSize = 13.sp)
                    }

                }

            }

        }

    }
}