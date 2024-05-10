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
        fetchAllUsers()
    }

    fun filterUsersByAdmin(
        searchQuery: String,
        userType: String
    ) {
        viewModelScope.launch {
            if (searchQuery.isEmpty() && userType == "All") {
                fetchAllUsers()
            } else if (searchQuery.isEmpty() && userType != "All") {
                offlineUserRepository.filterUsersByUserType(userType).collect { users ->
                    _users.value = users
                }
            } else if (searchQuery.isNotEmpty() && userType == "All") {
                offlineUserRepository.filterUsersByQuery(searchQuery).collect { users ->
                    _users.value = users
                }
            } else if (searchQuery.isNotEmpty() && userType != "All") {
                offlineUserRepository.filterUsersByQueryAndUserType(searchQuery, userType).collect { users ->
                    _users.value = users
                }
            }
        }
    }

    // Function to get all users for the admin
    private fun fetchAllUsers() {
        viewModelScope.launch {
            offlineUserRepository.getAllUsers().collect { users ->
                _users.value = users
            }
        }
    }


    fun deleteUser(user: User) {
        viewModelScope.launch {
            offlineUserRepository.deleteStudent(user)
            // Optionally, you can reload the user list after deletion
            fetchAllUsers()
        }
    }

    fun deactivateUser(user: User) {
        viewModelScope.launch {
            // Update the status of the user to "Inactive"
            val updatedUser = user.copy(status = "Inactive")
            offlineUserRepository.updateStudent(updatedUser)
            // Optionally, you can reload the user list after updating status
            fetchAllUsers()
        }
    }

    fun reactivateUser(user: User) {
        viewModelScope.launch {
            // Update the status of the user to "Active"
            val updatedUser = user.copy(status = "Active")
            offlineUserRepository.updateStudent(updatedUser)
            // Optionally, you can reload the user list after updating status
            fetchAllUsers()
        }
    }
}