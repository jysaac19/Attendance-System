package com.attendanceapp2.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.user.UserRepository
import com.attendanceapp2.universal.data.LoggedInUser
import com.attendanceapp2.universal.data.LoggedInUserHolder
import com.attendanceapp2.universal.viewmodel.SubjectViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val userRepo: UserRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

    suspend fun validateSignIn(email: String, password: String): Login {
        val user = userRepo.getUserByEmailAndPassword(email, password)
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