package attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchStudentViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository
): ViewModel() {

    private val _students: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val students: StateFlow<List<User>> = _students

    fun searchStudents(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                updateOfflineUsers()
            } else {
                offlineUserRepository.searchUser(query).collect { students ->
                    _students.value = students
                }
            }
        }
    }

    private fun updateOfflineUsers() {
        viewModelScope.launch {
            offlineUserRepository.deleteAllUsers()
            val users = onlineUserRepository.getAllUsers()
            users.forEach {
                offlineUserRepository.insertUser(it)
            }

            offlineUserRepository.getStudents().collect() {
                _students.value = it
            }
        }
    }
}