package attendanceappusers.facultyapp.screens.mainscreen.attendances

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

class FacultyAttendanceViewModel(
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    private val _attendances = MutableStateFlow<List<Attendance>>(emptyList())
    val attendances = _attendances.asStateFlow()

    private val _subjects = MutableStateFlow<List<String>>(emptyList())
    val subjects = _subjects.asStateFlow()

    init {
        fetchSubjectsForLoggedInUser()
    }

    private fun fetchSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            val userId = loggedInUser!!.userId
            val subjectIds = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForFaculty(userId)
            if (subjectIds.isNotEmpty()) {
                val subjects = offlineSubjectRepository.getSubjectsByIds(subjectIds)
                    .map { it.code }
                _subjects.value = listOf("All") + subjects
            }
        }
    }

    suspend fun filterAttendance(
        selectedSubject: String,
        startDate: String,
        endDate: String
    ) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        if (selectedSubject == "All") {
            val subjectIds = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForFaculty(loggedInUser!!.userId)
            offlineAttendanceRepository.filterAttendancesBySubjectIdsAndDateRange(subjectIds, startDate, endDate).collect { attendances ->
                _attendances.value = attendances
            }
        } else if (selectedSubject != "All") {
            offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(
                startDate = startDate,
                endDate = endDate,
                subjectCode = selectedSubject
            ).collect { attendances ->
                _attendances.value = attendances
            }
        }
    }
}