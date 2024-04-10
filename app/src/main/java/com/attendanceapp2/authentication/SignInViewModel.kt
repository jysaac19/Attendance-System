package com.attendanceapp2.authentication

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
    fun validateSignIn(email: String, password: String): Login {
        val user = users.find { it.email == email && it.password == password }
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
            LoggedInUserHolder.setLoggedInUser(loggedInUser) // Set the user as loggedInUser
            Login.Successfully(loggedInUser)
        } else {
            Login.Failed("Invalid email or password")
        }
    }
}


sealed class Login {
    data class Failed(val errorMessage: String) : Login()
    data class Successfully(val message: LoggedInUser) : Login()
}