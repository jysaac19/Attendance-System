package com.attendanceapp2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.universaldata.LoggedInUser
import com.attendanceapp2.universaldata.LoggedInUserHolder
import com.attendanceapp2.universalscreencomponents.navigation.AppNavigation
import com.attendanceapp2.universalscreencomponents.navigation.faculty.FacultyNavigation
import com.attendanceapp2.universalscreencomponents.navigation.student.StudentNavigation
import com.attendanceapp2.theme.NBSCollegeTheme
import com.attendanceapp2.viewmodel.AppViewModelProvider
import com.shin.myproject.ViewModel.ScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val screenViewModel: ScreenViewModel by viewModels {
        AppViewModelProvider.Factory
    }

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Handle double tap back button to close the app
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Press back again to exit",
                        Toast.LENGTH_SHORT
                    ).show()
                    backPressedOnce = true

                    // Reset the flag after a delay (e.g., 2 seconds)
                    lifecycleScope.launch {
                        delay(2000)
                        backPressedOnce = false
                    }
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setContent {
            val loggedInUser by LoggedInUserHolder.loggedInUser.collectAsState()
            val navController: NavController = rememberNavController()


            NBSCollegeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
//                    NewSchedule(navController)
                    when (val user = loggedInUser) {
                        is LoggedInUser -> {
                            when (user.usertype) {
                                "Student" -> {
                                    StudentNavigation()
                                }
                                "Faculty" -> {
                                    FacultyNavigation()
                                }
                                "Admin" -> {
                                    FacultyNavigation()
                                }
                                else -> {
                                    AppNavigation()
                                }
                            }
                        }
                        else -> {
                            AppNavigation()
                        }
                    }
                }
            }
        }
    }
}