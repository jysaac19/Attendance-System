package com.attendanceapp2.authentication

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.attendanceapp2.approutes.AppRoutes
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.repositories.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed class LoginResult {
    object Success : LoginResult()
    data class Failure(val errorMessage: String) : LoginResult()
    data class UserType(val type: String) : LoginResult()
}

data class LoggedInUser(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val email: String,
    val usertype: String,
    val department: String
)

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

    var loggedInUser: LoggedInUser? = null

    fun checkUser(email: String, password: String): LoggedInUser? {
        users.forEach { user ->
            if (user.email == email && user.password == password) {
                return LoggedInUser(
                    user.id,
                    user.firstname,
                    user.lastname,
                    user.email,
                    user.usertype,
                    user.department
                )
            }
        }
        return null
    }
}