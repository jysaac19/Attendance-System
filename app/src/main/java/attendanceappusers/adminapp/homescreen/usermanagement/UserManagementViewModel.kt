package attendanceappusers.adminapp.homescreen.usermanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserManagementViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository
) : ViewModel() {
    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        updateOfflineUsers()
    }

    fun filterUsersByAdmin(searchQuery: String, userType: String) {
        viewModelScope.launch {
            if (searchQuery.isEmpty() && userType == "All") {
                updateOfflineUsers()
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

    fun updateOfflineUsers() {
        viewModelScope.launch {
            offlineUserRepository.deleteAllUsers()
            val users = onlineUserRepository.getAllUsers()
            users.forEach {
                offlineUserRepository.insertUser(it)
            }

            offlineUserRepository.getAllUsers().collect {
                _users.value = it
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            onlineUserRepository.deleteUser(user.id)
            updateOfflineUsers()
        }
    }

    fun deactivateUser(user: User) {
        viewModelScope.launch {
            val updatedUser = user.copy(status = "Inactive")
            onlineUserRepository.updateUser(updatedUser)
            updateOfflineUsers()
        }
    }

    fun reactivateUser(user: User) {
        viewModelScope.launch {
            val updatedUser = user.copy(status = "Active")
            onlineUserRepository.updateUser(updatedUser)
            updateOfflineUsers()
        }
    }
}