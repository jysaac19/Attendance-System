package com.attendanceapp2.appviewmodel.screenviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
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
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val offlineUserRepository: OfflineUserRepository
) : ViewModel() {

    fun logout(): Results.LoginResult {
        return try {
            LoggedInUserHolder.clearLoggedInUser()
            SelectedSubjectHolder.clearSelectedSubject()
            ScannedQRCodeHolder.clearScannedQRCode()
            SelectedStudentHolder.clearSelectedStudent()
            UpdatingSubjectHolder.clearSelectedSubject()
            UpdatingUserHolder.clearSelectedUser()
            Results.LoginResult(successMessage = "Logged out successfully")
        } catch (e: Exception) {
            Results.LoginResult(failureMessage = "Failed to log out")
        }
    }

    suspend fun deactivate(): Results.UpdateUserResult {
        return try {
            val loggedInUser = LoggedInUserHolder.getLoggedInUser()
            if (loggedInUser != null) {
                offlineUserRepository.updateStudent(
                    User(
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
            Results.UpdateUserResult(successMessage = "Account deactivated successfully")
        } catch (e: Exception) {
            Results.UpdateUserResult(failureMessage = "Failed to deactivate account")
        }
    }

    suspend fun delete(): Results.UpdateUserResult {
        return try {
            val loggedInUser = LoggedInUserHolder.getLoggedInUser()
            if (loggedInUser != null) {
                offlineUserRepository.deleteStudent(
                    User(
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
            Results.UpdateUserResult(successMessage = "Account deleted successfully")
        } catch (e: Exception) {
            Results.UpdateUserResult(failureMessage = "Failed to delete account")
        }
    }
}