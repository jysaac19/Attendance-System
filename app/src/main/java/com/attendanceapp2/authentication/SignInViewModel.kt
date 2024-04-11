package com.attendanceapp2.authentication

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.repositories.user.UserRepository
import com.attendanceapp2.viewmodel.LoggedInUser
import com.attendanceapp2.viewmodel.LoggedInUserHolder

class SignInViewModel(
    private val userRepo: UserRepository
) : ViewModel() {

    private val users = listOf(
        User(
            101,
            "Je",
            "Ysaac",
            "k",
            "123",
            "Student",
            "ComSci"
        ),
        User(
            201,
            "Admin",
            "Ysaac",
            "j",
            "123",
            "Admin",
            "ComSci"
        ),
        User(
            301,
            "Faculty",
            "Ysaac",
            "s",
            "123",
            "Faculty",
            "ComSci"
        )
    )

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