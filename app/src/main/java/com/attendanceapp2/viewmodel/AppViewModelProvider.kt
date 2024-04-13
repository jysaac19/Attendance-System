package com.attendanceapp2.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.attendanceapp2.NBSAttendanceApp
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpViewModel
import com.attendanceapp2.data.screen.subject.NewSubjectViewModel
import com.attendanceapp2.posts.viewmodel.PostViewModel
import com.attendanceapp2.users.facultyapp.viewmodel.QRGeneratorViewModel
import com.attendanceapp2.users.studentapp.screens.mainscreens.scanner.ScannerViewModel
import com.attendanceapp2.users.studentapp.viewmodel.SubjectViewModel
import com.shin.myproject.ViewModel.ScreenViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // ScreenViewModel
        initializer {
            ScreenViewModel()
        }

        // ScannerViewModel
        initializer {
            ScannerViewModel(nbsAttendanceApplication().container.attendanceRepository)
        }

        //QR Generator ViewModel
        initializer {
            QRGeneratorViewModel(nbsAttendanceApplication().container.subjectRepository)
        }

        //Sign In ViewModel
        initializer {
            SignInViewModel(
                nbsAttendanceApplication().container.userRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.subjectRepository
                )
            )
        }

        //Sign Up ViewModel
        initializer {
            SignUpViewModel(nbsAttendanceApplication().container.userRepository)
        }

        //Subject ViewModel
        initializer {
            SubjectViewModel(
                nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                nbsAttendanceApplication().container.subjectRepository
            )
        }

        //Posts ViewModel [sample ktor implementation]
        initializer {
            PostViewModel(nbsAttendanceApplication().container.onlinePostRepository)
        }

        //New Subject ViewModel
        initializer {
            NewSubjectViewModel(
                nbsAttendanceApplication().container.subjectRepository
            )
        }

        //Profile ViewModel
        initializer {
            ProfileViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NBSApplication].
 */
fun CreationExtras.nbsAttendanceApplication(): NBSAttendanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NBSAttendanceApp)