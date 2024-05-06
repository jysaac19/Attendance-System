package com.attendanceapp2.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.user.LoggedInUser
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

    suspend fun validateSignIn(email: String, password: String): Login {
        val user = offlineUserRepository.getUserByEmailAndPassword(email, password)
        return if (user != null) {
            val loggedInUser = LoggedInUser(
                userId = user.id,
                firstname = user.firstname,
                lastname = user.lastname,
                email = user.email,
                password = user.password,
                usertype = user.usertype,
                department = user.department
            )
            LoggedInUserHolder.setLoggedInUser(loggedInUser)
            Log.d("SignInViewModel", "Login successful. User: $loggedInUser")

            // Fetch subject IDs for the logged-in user
            CoroutineScope(Dispatchers.IO).launch {
                subjectViewModel.fetchSubjectsForLoggedInUser()
            }

            Login.Successfully(loggedInUser)
        } else {
            Log.d("SignInViewModel", "Login failed. Invalid email or password")
            Login.Failed("Invalid email or password")
        }
    }
}


sealed class Login {
    data class Failed(val errorMessage: String) : Login()
    data class Successfully(val message: LoggedInUser) : Login()
}