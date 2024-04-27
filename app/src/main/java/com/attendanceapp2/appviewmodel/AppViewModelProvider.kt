package com.attendanceapp2.appviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.attendanceapp2.NBSAttendanceApp
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpViewModel
import com.attendanceapp2.data.screen.subject.NewSubjectViewModel
import com.attendanceapp2.posts.viewmodel.PostViewModel
import com.attendanceapp2.universal.viewmodel.ProfileViewModel
import com.attendanceapp2.universal.viewmodel.ScreenViewModel
import com.attendanceapp2.universal.viewmodel.SubjectViewModel
import com.attendanceapp2.user.facultyapp.screens.mainscreen.qrscreen.FacultyQRGeneratorViewModel
import com.attendanceapp2.user.facultyapp.screens.mainscreen.subjects.viewmodel.FacultySubjectAttendancesViewModel
import com.attendanceapp2.user.facultyapp.viewmodel.FacultyAttendanceViewModel
import com.attendanceapp2.user.facultyapp.viewmodel.FacultySubjectAttendanceViewModel
import com.attendanceapp2.user.studentapp.viewmodel.ScannerViewModel
import com.attendanceapp2.user.studentapp.viewmodel.StudentAttendanceViewModel
import com.attendanceapp2.user.studentapp.viewmodel.StudentSubjectAttendanceViewModel
import com.attendanceapp2.user.studentapp.viewmodel.StudentSubjectViewModel

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
            ScannerViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository
            )
        }

        //QR Generator ViewModel
        initializer {
            FacultyQRGeneratorViewModel()
        }

        //Sign In ViewModel
        initializer {
            SignInViewModel(
                nbsAttendanceApplication().container.userRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.offlineSubjectRepository
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
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        //Subject ViewModel for Student
        initializer {
            StudentSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.offlineSubjectRepository
                )
            )
        }

        initializer {
            FacultySubjectAttendancesViewModel(nbsAttendanceApplication().container.offlineAttendanceRepository)
        }

        //Posts ViewModel [sample ktor implementation]
        initializer {
            PostViewModel(nbsAttendanceApplication().container.onlinePostRepository)
        }

        //New Subject ViewModel
        initializer {
            NewSubjectViewModel(
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        //Profile ViewModel
        initializer {
            ProfileViewModel()
        }

        initializer {
            StudentAttendanceViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            StudentSubjectAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository
            )
        }

        initializer {
            FacultyAttendanceViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            FacultySubjectAttendanceViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NBSApplication].
 */
fun CreationExtras.nbsAttendanceApplication(): NBSAttendanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NBSAttendanceApp)