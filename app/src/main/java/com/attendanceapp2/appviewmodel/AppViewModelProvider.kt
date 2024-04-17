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
import com.attendanceapp2.universal.viewmodel.AttendanceViewModel
import com.attendanceapp2.universal.viewmodel.ProfileViewModel
import com.attendanceapp2.universal.viewmodel.ScreenViewModel
import com.attendanceapp2.universal.viewmodel.SubjectViewModel
import com.attendanceapp2.user.facultyapp.screens.mainscreen.qrscreen.FacultyQRGeneratorViewModel
import com.attendanceapp2.user.facultyapp.screens.mainscreen.subjects.viewmodel.FacultySubjectAttendancesViewModel
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
                nbsAttendanceApplication().container.attendanceRepository,
                AttendanceViewModel(
                    nbsAttendanceApplication().container.attendanceRepository,
                    SubjectViewModel(
                        nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                        nbsAttendanceApplication().container.subjectRepository
                    )
                )
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

        //Subject ViewModel for Student
        initializer {
            StudentSubjectViewModel(
                nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                nbsAttendanceApplication().container.subjectRepository,
                nbsAttendanceApplication().container.attendanceRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.subjectRepository
                )
            )
        }

        initializer {
            FacultySubjectAttendancesViewModel(nbsAttendanceApplication().container.attendanceRepository)
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

        //Attendance ViewModel
        initializer {
            AttendanceViewModel(
                nbsAttendanceApplication().container.attendanceRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.subjectRepository
                )
            )
        }

        initializer {
            StudentAttendanceViewModel(
                nbsAttendanceApplication().container.attendanceRepository,
                AttendanceViewModel(
                    nbsAttendanceApplication().container.attendanceRepository,
                    SubjectViewModel(
                        nbsAttendanceApplication().container.userSubjectCrossRefRepository,
                        nbsAttendanceApplication().container.subjectRepository
                    )
                )
            )
        }

        initializer {
            StudentSubjectAttendanceViewModel(
                nbsAttendanceApplication().container.attendanceRepository
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