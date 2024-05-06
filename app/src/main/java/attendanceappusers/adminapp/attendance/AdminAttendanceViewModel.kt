package attendanceappusers.adminapp.attendance

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class AdminAttendanceViewModel(
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) :ViewModel() {
    private val _adminAttendanceList = MutableStateFlow<List<Attendance>>(emptyList())
    val adminAttendanceList = _adminAttendanceList.asStateFlow()


    // Use MutableStateFlow for subjects
    private val _subjects = MutableStateFlow<List<String>>(emptyList())
    val subjects = _subjects.asStateFlow()

    init {
        // Fetch subjects for logged-in user
        fetchAllSubjects()
    }

    // Function to fetch subjects associated with the logged-in user
    private fun fetchAllSubjects() {
        viewModelScope.launch {
            // Retrieve subject IDs for the admin
            val subjectIds = offlineUserSubjectCrossRefRepository.getAllSubjects()
            if (subjectIds.isNotEmpty()) {
                // Fetch subject codes using subject IDs
                val subjects = offlineSubjectRepository.getSubjectsByIds(subjectIds)
                    .map { it.code } // Assuming subject has a property 'code' representing its code
                _subjects.value = listOf("All") + subjects // Add "All" option to the beginning
            } else {
                // No subjects found for the admin
                _subjects.value = emptyList()
            }
        }
    }

    suspend fun fetchAllAttendance(selectedSubject: String, startDate: LocalDate, endDate: LocalDate) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            if (selectedSubject == "All") {
                // Fetch all subjects for the user
                val subjectIds = offlineUserSubjectCrossRefRepository.getAllSubjects()
                Log.d("AdminAttendanceViewModel", "Subject IDs: $subjectIds")
                if (subjectIds.isNotEmpty()) {
                    // Fetch attendances for all subjects
                    offlineAttendanceRepository.getAttendances(subjectIds).collect { attendances ->
                        _adminAttendanceList.value = attendances
                        Log.d("AdminAttendanceViewModel", "All Attendances: $attendances")
                    }
                } else {
                    // No subjects found for the user
                    _adminAttendanceList.value = emptyList()
                }
            } else {
                // Fetch attendances for the selected subject
                offlineAttendanceRepository.filterAttendance(
                    startDate.toString(),
                    endDate.toString(),
                    selectedSubject
                ).collect { attendances ->
                    _adminAttendanceList.value = attendances
                    Log.d("AdminAttendanceViewModel", "Subject Attendances: $attendances")
                }
            }
        }
    }

    fun filterAttendanceList(
        userId: String,
        subjectCode: String,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch {
            val filteredAttendances = if (subjectCode == "All") {
                offlineAttendanceRepository.filterAttendancesByAdmin(
                    userId,
                    startDate,
                    endDate
                )
            } else {
                offlineAttendanceRepository.filterStudentAttendance(
                    startDate,
                    endDate,
                    userId.toLong(),
                    subjectCode
                )
            }.first() // Assuming Flow emits a single list

            _adminAttendanceList.value = filteredAttendances
        }
    }
}