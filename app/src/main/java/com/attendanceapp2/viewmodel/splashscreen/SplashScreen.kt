package com.attendanceapp2.viewmodel.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.attendanceapp2.R
import com.shin.myproject.ViewModel.ScreenViewModel

@Composable
fun SplashScreen(
    screenViewModel: ScreenViewModel,
    content : @Composable () -> Unit
) {
    val state = screenViewModel.splashLoaded.collectAsState()
    screenViewModel.runSplashScreen()

    if (state.value) {
        content()
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.size(300.dp),
                painter = painterResource(id = R.drawable.nbslogo),
                contentDescription = "NBS LOGO"
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Box(Modifier.height(25.dp))
            LinearProgressIndicator(
                modifier = Modifier.width(150.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                trackColor = MaterialTheme.colorScheme.secondary,
            )
            // Run the resetSubjectRegistered function after a delay
            LaunchedEffect(Unit) {
                screenViewModel.resetSplashLoaded()
            }
        }
    }
}
