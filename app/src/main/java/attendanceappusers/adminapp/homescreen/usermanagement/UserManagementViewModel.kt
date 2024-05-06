package attendanceappusers.adminapp.homescreen.usermanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserManagementViewModel(
    private val offlineUserRepository: OfflineUserRepository
) : ViewModel() {

    // List of users
    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        // Load users when ViewModel is initialized
        fetchUsersForAdmin()
    }

    fun filterUsersByAdmin(
        userId: String,
        userType: String
    ) {
        viewModelScope.launch {
            if (userId.isEmpty() && userType == "All") {
                // If search text is empty and userType is "All", fetch all users
                fetchUsersForAdmin()
            } else if (userId.isNotEmpty() && userType == "All") {
                // If search text is not empty and userType is "All", filter users by starting userId
                val userIdPrefix = "$userId%"
                offlineUserRepository.filterUsersByStartingUserId(userIdPrefix).collect { users ->
                    // Update the StateFlow with the filtered users
                    _users.value = users
                }
            } else {
                // Call the repository function to filter users by userType and/or userId
                offlineUserRepository.filterUsersByAdmin(
                    userId,
                    userType
                ).collect { users ->
                    // Update the StateFlow with the filtered users
                    _users.value = users
                }
            }
        }
    }

    // Function to get all users for the admin
    private fun fetchUsersForAdmin() {
        viewModelScope.launch {
            offlineUserRepository.getUsers().collect { users ->
                _users.value = users
            }
        }
    }


    fun deleteUser(user: User) {
        viewModelScope.launch {
            offlineUserRepository.deleteStudent(user)
            // Optionally, you can reload the user list after deletion
            fetchUsersForAdmin()
        }
    }

    fun deactivateUser(user: User) {
        viewModelScope.launch {
            // Update the status of the user to "Inactive"
            val updatedUser = user.copy(status = "Inactive")
            offlineUserRepository.updateStudent(updatedUser)
            // Optionally, you can reload the user list after updating status
            fetchUsersForAdmin()
        }
    }

    fun reactivateUser(user: User) {
        viewModelScope.launch {
            // Update the status of the user to "Active"
            val updatedUser = user.copy(status = "Active")
            offlineUserRepository.updateStudent(updatedUser)
            // Optionally, you can reload the user list after updating status
            fetchUsersForAdmin()
        }
    }
}