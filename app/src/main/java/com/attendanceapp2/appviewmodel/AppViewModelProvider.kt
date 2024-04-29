package com.attendanceapp2.appviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import attendanceappusers.adminapp.adduser.AddUserViewModel
import attendanceappusers.adminapp.attendance.AdminAttendanceViewModel
import attendanceappusers.adminapp.profile.AdminProfileViewModel
import attendanceappusers.facultyapp.screens.mainscreen.qrscreen.FacultyQRGeneratorViewModel
import attendanceappusers.facultyapp.screens.mainscreen.subjects.viewmodel.FacultySubjectAttendancesViewModel
import attendanceappusers.facultyapp.viewmodel.FacultyAttendanceViewModel
import attendanceappusers.facultyapp.viewmodel.FacultySubjectAttendanceViewModel
import attendanceappusers.studentapp.viewmodel.ScannerViewModel
import attendanceappusers.studentapp.viewmodel.StudentAttendanceViewModel
import attendanceappusers.studentapp.viewmodel.StudentSubjectAttendanceViewModel
import attendanceappusers.studentapp.viewmodel.StudentSubjectViewModel
import com.attendanceapp2.NBSAttendanceApp
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpViewModel
import com.attendanceapp2.data.screen.subject.NewSubjectViewModel
import com.attendanceapp2.posts.viewmodel.PostViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.ProfileViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.ScreenViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel

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
                nbsAttendanceApplication().container.offlineUserRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.offlineSubjectRepository
                )
            )
        }

        //Sign Up ViewModel
        initializer {
            SignUpViewModel(nbsAttendanceApplication().container.offlineUserRepository)
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
            AdminProfileViewModel()
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

        initializer {
            AdminAttendanceViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            AddUserViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NBSApplication].
 */
fun CreationExtras.nbsAttendanceApplication(): NBSAttendanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NBSAttendanceApp)