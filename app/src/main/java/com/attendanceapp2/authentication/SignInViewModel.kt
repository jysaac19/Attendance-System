package com.attendanceapp2.authentication

import androidx.lifecycle.ViewModel
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.user.LoggedInUser
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val onlineUserRepository: OnlineUserRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

    suspend fun validateSignIn(email: String, password: String): Results.LoginResult {
        return try {
            val user = try {
                onlineUserRepository.getUserByEmailPassword(email, password)
            } catch (e: Exception) {
                null
            }

            if (user != null) {
                LoggedInUserHolder.setLoggedInUser(
                    LoggedInUser(
                        id = user.id,
                        firstname = user.firstname,
                        lastname = user.lastname,
                        email = user.email,
                        password = user.password,
                        usertype = user.usertype,
                        department = user.department,
                        status = user.status
                    )
                )

                try {
                    CoroutineScope(Dispatchers.IO).launch {
                        subjectViewModel.fetchActiveSubjectsForLoggedInUser()
                        subjectViewModel.fetchArchivedSubjectsForLoggedInUser()
                    }
                } catch (e: Exception) {
                    // Handle any exceptions that occur during subject fetching
                }

                Results.LoginResult(successMessage = "Login successful.")
            } else {
                Results.LoginResult(failureMessage = "Invalid email or password")
            }
        } catch (e: Exception) {
            Results.LoginResult(failureMessage = "An unexpected error occurred: ${e.message}")
        }
    }
}