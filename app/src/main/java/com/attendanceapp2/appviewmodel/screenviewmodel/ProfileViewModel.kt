package com.attendanceapp2.appviewmodel.screenviewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.attendance.ScannedQRCodeHolder
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.UpdatingSubjectHolder
import com.attendanceapp2.data.model.user.LoggedInUserHolder.loggedInUser
import com.attendanceapp2.data.model.user.SelectedStudentHolder
import com.attendanceapp2.data.model.user.UpdatingUserHolder
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.flow.first

class ProfileViewModel (
    private val offlineUserRepository: OfflineUserRepository
) : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
        SelectedSubjectHolder.clearSelectedSubject()
        ScannedQRCodeHolder.clearScannedQRCode()
        SelectedStudentHolder.clearSelectedStudent()
        UpdatingSubjectHolder.clearSelectedSubject()
        UpdatingUserHolder.clearSelectedUser()
    }

    suspend fun deactivate() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()

        if (loggedInUser != null) {
            offlineUserRepository.updateStudent(
                User (
                    id = loggedInUser.id,
                    firstname = loggedInUser.firstname,
                    lastname = loggedInUser.lastname,
                    email = loggedInUser.email,
                    password = loggedInUser.password,
                    usertype = loggedInUser.usertype,
                    department = loggedInUser.department,
                    status = "Inactive"
                )
            )
        }
    }

    suspend fun delete() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()

        if (loggedInUser != null) {
            offlineUserRepository.deleteStudent(
                User (
                    id = loggedInUser.id,
                    firstname = loggedInUser.firstname,
                    lastname = loggedInUser.lastname,
                    email = loggedInUser.email,
                    password = loggedInUser.password,
                    usertype = loggedInUser.usertype,
                    department = loggedInUser.department,
                    status = loggedInUser.status
                )
            )
        }
    }
}