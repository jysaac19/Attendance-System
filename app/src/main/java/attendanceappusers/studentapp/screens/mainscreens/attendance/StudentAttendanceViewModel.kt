package attendanceappusers.studentapp.screens.mainscreens.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentAttendanceViewModel(
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    private val _attendances = MutableStateFlow<List<Attendance>>(emptyList())
    val attendances = _attendances.asStateFlow()

    suspend fun filterStudentAttendances(subjectCode: String, startDate: String, endDate: String) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            if (subjectCode == "All") {
                offlineAttendanceRepository.filterStudentAttendanceByDateRange(
                    startDate,
                    endDate,
                    loggedInUser!!.userId
                ).collect { attendances ->
                    _attendances.value = attendances
                }
            } else {
                offlineAttendanceRepository.filterStudentAttendanceBySubjectCodeAndDateRange(
                    startDate,
                    endDate,
                    loggedInUser!!.userId,
                    subjectCode
                ).collect { attendances ->
                    _attendances.value = attendances
                }
            }
        }
    }

    private val _subjects = MutableStateFlow<List<String>>(emptyList())
    val subjects = _subjects.asStateFlow()

    init {
        fetchSubjectsForLoggedInUser()
    }

    private fun fetchSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            val subjectIds = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForFaculty(loggedInUser!!.userId)
            if (subjectIds.isNotEmpty()) {
                val subjects = offlineSubjectRepository.getSubjectsByIds(subjectIds)
                    .map { it.code }
                _subjects.value = listOf("All") + subjects
            } else {
                _subjects.value = emptyList()
            }
        }
    }
}