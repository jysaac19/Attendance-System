package com.attendanceapp2.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.user.LoggedInUser
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

    suspend fun validateSignIn(email: String, password: String): Results.LoginResult {
        val user = offlineUserRepository.getUserByEmailAndPassword(email, password)
        return if (user != null) {
            val loggedInUser = LoggedInUser(
                id = user.id,
                firstname = user.firstname,
                lastname = user.lastname,
                email = user.email,
                password = user.password,
                usertype = user.usertype,
                department = user.department,
                status = user.status
            )
            LoggedInUserHolder.setLoggedInUser(loggedInUser)

            // Fetch subject IDs for the logged-in user
            CoroutineScope(Dispatchers.IO).launch {
                subjectViewModel.fetchSubjectsForLoggedInUser()
            }

            Results.LoginResult(successMessage = "Login successful.")
        } else {
            Results.LoginResult(failureMessage = "Invalid email or password")
        }
    }
}