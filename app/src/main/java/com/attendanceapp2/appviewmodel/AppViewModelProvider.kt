package com.attendanceapp2.appviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import attendanceappusers.adminapp.attendance.AdminAttendanceViewModel
import attendanceappusers.adminapp.homescreen.attendancemanagement.AttendanceManagementViewModel
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent.SearchStudentViewModel
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.SearchSubjectViewModel
import attendanceappusers.adminapp.homescreen.subjectmanagement.SubjectManagementViewModel
import attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject.AddSubjectViewModel
import attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject.UpdateSubjectViewModel
import attendanceappusers.adminapp.homescreen.usermanagement.UserManagementViewModel
import attendanceappusers.adminapp.homescreen.usermanagement.adduser.AddUserViewModel
import attendanceappusers.adminapp.homescreen.usermanagement.updateuser.UpdateUserViewModel
import attendanceappusers.adminapp.profile.AdminProfileViewModel
import attendanceappusers.adminapp.subject.addschedule.AddScheduleViewModel
import attendanceappusers.adminapp.subject.adminsubject.AdminSubjectViewModel
import attendanceappusers.adminapp.subject.adminsubjectattendacne.AdminSubjectAttendanceViewModel
import attendanceappusers.adminapp.subject.adminsubjectlist.AdminSubjectListViewModel
import attendanceappusers.facultyapp.screens.mainscreen.qrscreen.FacultyQRGeneratorViewModel
import attendanceappusers.facultyapp.screens.mainscreen.attendances.FacultyAttendanceViewModel
import attendanceappusers.facultyapp.screens.mainscreen.subjects.facultysubjectattendances.FacultySubjectAttendanceViewModel
import attendanceappusers.studentapp.screens.subjects.joinsubject.JoinSubjectViewModel
import attendanceappusers.studentapp.viewmodel.ScannerViewModel
import attendanceappusers.studentapp.screens.mainscreens.attendance.StudentAttendanceViewModel
import attendanceappusers.studentapp.screens.subjects.StudentSubjectAttendanceViewModel
import attendanceappusers.studentapp.viewmodel.StudentSubjectViewModel
import com.attendanceapp2.NBSAttendanceApp
import com.attendanceapp2.appviewmodel.screenviewmodel.ProfileViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.ScreenViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpViewModel
import com.attendanceapp2.data.screen.subject.NewSubjectViewModel
import com.attendanceapp2.posts.viewmodel.PostViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // ScreenViewModel
        initializer {
            ScreenViewModel()
        }

        initializer {
            JoinSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
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
                nbsAttendanceApplication().container.offlineAttendanceRepository
            )
        }


        //Scope: Admin
        initializer {
            AdminAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            AddSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            AddUserViewModel(
                nbsAttendanceApplication().container.offlineUserRepository
            )
        }

        initializer {
            SearchSubjectViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            SearchStudentViewModel(
                nbsAttendanceApplication().container.offlineUserRepository
            )
        }

        initializer {
            UpdateUserViewModel(
                nbsAttendanceApplication().container.offlineUserRepository
            )
        }

        initializer {
            UpdateSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository
            )
        }

        initializer {
            AdminSubjectListViewModel(
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            AdminSubjectViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineScheduleRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            AddScheduleViewModel(
                nbsAttendanceApplication().container.offlineScheduleRepository
            )
        }

        initializer {
            AdminSubjectAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository
            )
        }

        initializer {
            UserManagementViewModel(
                nbsAttendanceApplication().container.offlineUserRepository
            )
        }

        initializer {
            AttendanceManagementViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        initializer {
            SubjectManagementViewModel(
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