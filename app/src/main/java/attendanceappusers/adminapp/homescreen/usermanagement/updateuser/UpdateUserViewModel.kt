package attendanceappusers.adminapp.homescreen.usermanagement.updateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import kotlinx.coroutines.launch

class UpdateUserViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository
): ViewModel() {
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

    fun updateOnlineUser(updatedUser: User) {
        viewModelScope.launch {
            onlineUserRepository.updateUser(updatedUser)
            updateOfflineUsers()
        }
    }

    fun validateFields(user: User): Results.UpdateUserResult {
        return when {
            user.firstname.isBlank() || user.lastname.isBlank() || user.email.isBlank() ||
                    user.password.isBlank() || user.usertype.isBlank() || user.department.isBlank() ||
                    user.status.isBlank() -> Results.UpdateUserResult(failureMessage = "All fields are required.")
            user.password.length < 8 -> Results.UpdateUserResult(failureMessage = "Password must be at least 8 characters long.")
            else -> Results.UpdateUserResult(successMessage = "Fields are valid.")
        }
    }
}