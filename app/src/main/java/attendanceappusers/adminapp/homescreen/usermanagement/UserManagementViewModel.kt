package attendanceappusers.adminapp.homescreen.usermanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.flow.Flow
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

    // Function to get all users for the admin
    private fun fetchUsersForAdmin() {
        viewModelScope.launch {
            offlineUserRepository.getUsers().collect { users ->
                _users.value = users
            }
        }
    }
}