package attendanceappusers.adminapp.homescreen.usermanagement.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import kotlinx.coroutines.launch

class AddUserViewModel (
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository
) : ViewModel() {
    init {
        updateOfflineUsers()
    }

    private fun updateOfflineUsers() {
        viewModelScope.launch {
            offlineUserRepository.deleteAllUsers()
            val users = onlineUserRepository.getAllUsers()
            users.forEach {
                offlineUserRepository.insertUser(it)
            }
        }
    }

    suspend fun registerUser(firstname: String, lastname: String, email: String, password: String, usertype: String, department: String, status: String): Results.AddUserResult {
        updateOfflineUsers()

        // Check if firstname, lastname, and email are unique
        if (offlineUserRepository.getUserByEmail(email) != null) {
            return Results.AddUserResult(failureMessage = "User with this email already exists")
        }

        // Check if password meets the length requirement
        if (password.length < 8) {
            return Results.AddUserResult(failureMessage = "Password must be at least 8 characters long")
        }

        // Check if firstname and lastname combination is unique
        if (offlineUserRepository.getUserByFullName(firstname, lastname) != null) {
            return Results.AddUserResult(failureMessage = "User with this firstname and lastname already exists")
        }

        onlineUserRepository.addUser(
            User(
                id = 0,
                firstname = firstname,
                lastname = lastname,
                email = email,
                password = password,
                usertype = usertype,
                department = department,
                status = status
            )
        )

        return Results.AddUserResult(successMessage = "User added successfully")
    }
}