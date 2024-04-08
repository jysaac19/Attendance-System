package com.attendanceapp2.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.attendanceapp2.NBSAttendanceApp
import com.attendanceapp2.screens.mainscreens.scanner.ScannerViewModel
import com.shin.myproject.ViewModel.ScreenViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for RegistrationViewModel
//        initializer {
//            RegisterViewModel(
//                  nbsAttendanceApplication().container.userRepository
//            )
//        }
//
//        // Initializer for LoginViewModel
//        initializer {
//            LoginViewModel(
//                nbsAttendanceApplication().container.userRepository,
//            )
//        }

        // ScreenViewModel
        initializer {
            ScreenViewModel()
        }
        // ScannerViewModel
        initializer {
            ScannerViewModel(nbsAttendanceApplication().container.studentRepository, nbsAttendanceApplication().container.attendanceRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NBSApplication].
 */
fun CreationExtras.nbsAttendanceApplication(): NBSAttendanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NBSAttendanceApp)