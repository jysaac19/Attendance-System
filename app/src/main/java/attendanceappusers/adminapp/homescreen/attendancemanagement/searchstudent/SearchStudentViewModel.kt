package attendanceappusers.adminapp.homescreen.attendancemanagement.searchstudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchStudentViewModel(
    private val offlineUserRepository: OfflineUserRepository
): ViewModel() {

    private val _students: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val students: StateFlow<List<User>> = _students

    init {
        // Load attendances when ViewModel is initialized
        fetchUsers()
    }

    // Function to search for users based on user ID, first name, or last name
    fun searchStudents(query: String): Flow<List<User>> {
        viewModelScope.launch {
            offlineUserRepository.searchStudents(query).collect { students ->
                _students.value = students
            }
        }
        return offlineUserRepository.searchStudents(query)
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            // Call the repository function to get all attendances
            offlineUserRepository.getStudents().collect() { users ->
                // Update the StateFlow with the fetched attendances
                _students.value = users
            }
        }
    }
}