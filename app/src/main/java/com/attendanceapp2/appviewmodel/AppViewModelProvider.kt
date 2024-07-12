package com.attendanceapp2.appviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import attendanceappusers.adminapp.attendance.AdminAttendanceViewModel
import attendanceappusers.adminapp.homescreen.AdminHomeScreenViewModel
import attendanceappusers.adminapp.homescreen.attendancemanagement.AttendanceManagementViewModel
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent.SearchStudentViewModel
import attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject.SearchSubjectViewModel
import attendanceappusers.adminapp.homescreen.subjectmanagement.SubjectManagementViewModel
import attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject.AddSubjectViewModel
import attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject.UpdateSubjectViewModel
import attendanceappusers.adminapp.homescreen.usermanagement.UserManagementViewModel
import attendanceappusers.adminapp.homescreen.usermanagement.adduser.AddUserViewModel
import attendanceappusers.adminapp.homescreen.usermanagement.updateuser.UpdateUserViewModel
import attendanceappusers.adminapp.subject.adminsubject.AdminSubjectViewModel
import attendanceappusers.adminapp.subject.adminsubjectattendance.AdminSubjectAttendanceViewModel
import attendanceappusers.adminapp.subject.adminsubjectlist.AdminSubjectListViewModel
import attendanceappusers.facultyapp.qrscreen.FacultyQRGeneratorViewModel
import attendanceappusers.facultyapp.attendances.FacultyAttendanceViewModel
import attendanceappusers.facultyapp.subjects.facultysubjectattendances.FacultySubjectAttendanceViewModel
import attendanceappusers.facultyapp.subjects.searchstudent.FacultySearchStudentViewModel
import attendanceappusers.studentapp.subjects.joinsubject.JoinSubjectViewModel
import attendanceappusers.studentapp.scanner.ScannerViewModel
import attendanceappusers.studentapp.attendance.StudentAttendanceViewModel
import attendanceappusers.adminapp.notification.NotificationViewModel
import attendanceappusers.studentapp.subjects.subjectattendances.StudentSubjectAttendanceViewModel
import attendanceappusers.studentapp.subjects.subjectinfo.StudentSubjectInfoViewModel
import com.attendanceapp2.NBSAttendanceApp
import com.attendanceapp2.appviewmodel.screenviewmodel.ProfileViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.ScreenViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.authentication.SignInViewModel
import com.attendanceapp2.authentication.SignUpViewModel
import com.attendanceapp2.screenuniversalcomponents.subject.NewSubjectViewModel

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
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        // ScannerViewModel
        initializer {
            ScannerViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository
            )
        }

        //QR Generator ViewModel
        initializer {
            FacultyQRGeneratorViewModel()
        }

        initializer {
            FacultySearchStudentViewModel(
                nbsAttendanceApplication().container.onlineUserRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,

                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository
            )
        }

        //Sign In ViewModel
        initializer {
            SignInViewModel(

                nbsAttendanceApplication().container.onlineUserRepository,
                SubjectViewModel(
                    nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.offlineSubjectRepository,

                    nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,
                    nbsAttendanceApplication().container.onlineSubjectRepository
                )
            )
        }

        //Sign Up ViewModel
        initializer {
            SignUpViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineUserRepository
            )
        }

        //Subject ViewModel
        initializer {
            SubjectViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        //New Subject ViewModel
        initializer {
            NewSubjectViewModel(
                nbsAttendanceApplication().container.offlineSubjectRepository
            )
        }

        //Profile ViewModel
        initializer {
            ProfileViewModel(
                nbsAttendanceApplication().container.offlineUserRepository
            )
        }

        initializer {
            StudentAttendanceViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            StudentSubjectInfoViewModel(
                nbsAttendanceApplication().container.offlineScheduleRepository,
                nbsAttendanceApplication().container.onlineScheduleRepository
            )
        }

        initializer {
            StudentSubjectAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository
            )
        }

        initializer {
            FacultyAttendanceViewModel(
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            FacultySubjectAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository
            )
        }


        //Scope: Admin
        initializer {
            AdminHomeScreenViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineScheduleRepository,
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,

                nbsAttendanceApplication().container.onlineUserRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineScheduleRepository,
                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository
            )
        }


        initializer {
            AdminAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            AddSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineUserRepository,
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            AddUserViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineUserRepository
            )
        }

        initializer {
            SearchSubjectViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            SearchStudentViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineUserRepository
            )
        }

        initializer {
            UpdateUserViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineUserRepository
            )
        }

        initializer {
            UpdateSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository,
                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository
            )
        }

        initializer {
            AdminSubjectListViewModel(
                nbsAttendanceApplication().container.offlineSubjectRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            AdminSubjectViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.offlineScheduleRepository,
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineUserSubjectCrossRefRepository,

                nbsAttendanceApplication().container.onlineScheduleRepository
            )
        }

        initializer {
            AdminSubjectAttendanceViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineAttendanceRepository
            )
        }

        initializer {
            UserManagementViewModel(
                nbsAttendanceApplication().container.offlineUserRepository,
                nbsAttendanceApplication().container.onlineUserRepository
            )
        }

        initializer {
            AttendanceManagementViewModel(
                nbsAttendanceApplication().container.offlineAttendanceRepository,
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineAttendanceRepository,
                nbsAttendanceApplication().container.onlineSubjectRepository
            )
        }

        initializer {
            SubjectManagementViewModel(
                nbsAttendanceApplication().container.offlineSubjectRepository,

                nbsAttendanceApplication().container.onlineSubjectRepository,
                nbsAttendanceApplication().container.onlineUserRepository,
                nbsAttendanceApplication().container.onlineUserSubjectCrossRefRepository

            )
        }

        initializer {
            NotificationViewModel(
                nbsAttendanceApplication().container.onlineNotifRepository
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