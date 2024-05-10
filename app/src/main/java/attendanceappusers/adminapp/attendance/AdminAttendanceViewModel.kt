package attendanceappusers.adminapp.attendance

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

class AdminAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    // List of attendances
    private val _attendances: MutableStateFlow<List<Attendance>> = MutableStateFlow(emptyList())
    val attendances: StateFlow<List<Attendance>> = _attendances

    fun filterAttendancesByAdmin(
        searchQuery: String,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch {
            if (searchQuery.isEmpty()) {
                getAllAttendances()
            } else {
                // Perform search based on user ID or full name
                offlineAttendanceRepository.filterAttendancesByAdmin(searchQuery, startDate, endDate).collect { attendances ->
                    // Update the StateFlow with the filtered users
                    _attendances.value = attendances
                }
            }
        }
    }

    // Function to fetch all attendances
    private fun getAllAttendances() {
        viewModelScope.launch {
            // Call the repository function to get all attendances
            offlineAttendanceRepository.getAllAttendances().collect() { attendances ->
                // Update the StateFlow with the fetched attendances
                _attendances.value = attendances
            }
        }
    }

    private val _subjects = MutableStateFlow<List<String>>(listOf())
    val subjects: StateFlow<List<String>> = _subjects.asStateFlow()

    init {
        getAllSubjectCodes()
    }
    private fun getAllSubjectCodes() {
        viewModelScope.launch {
            val subjectCodes = offlineSubjectRepository.getAllSubjects().map { subjects ->
                val subjectList = subjects.map { it.code }
                subjectList
            }.first()
            _subjects.value = listOf("All") + subjectCodes // Add "All" at the start of the list
        }
    }
}