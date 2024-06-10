package attendanceappusers.facultyapp.attendances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FacultyAttendanceViewModel(
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,

    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository
) : ViewModel() {

    private val _attendances = MutableStateFlow<List<Attendance>>(emptyList())
    val attendances = _attendances.asStateFlow()

    private val _subjects = MutableStateFlow<List<String>>(emptyList())
    val subjects = _subjects.asStateFlow()

    init {
        getSubjectCodesForLoggedInUser()
        updateOfflineAttendances()
        updateOfflineSubjects()
    }
    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val onlineSubjects = onlineSubjectRepository.getAllSubjects()
            onlineSubjects.forEach {
                offlineSubjectRepository.insertSubject(it)
            }
        }
    }

    private fun updateOfflineAttendances() {
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()
            val onlineAttendances = onlineAttendanceRepository.getAllAttendances()
            onlineAttendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
            }
        }
    }

    private fun getSubjectCodesForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            offlineUserSubjectCrossRefRepository.deleteAllUserSubjectCrossRefs()
            offlineSubjectRepository.deleteAllSubjects()

            val onlineUserSubjectCrossRefs = onlineUserSubjectCrossRefRepository.getAllUserSubCrossRef()
            val onlineSubjects = onlineSubjectRepository.getAllSubjects()

            onlineUserSubjectCrossRefs.forEach { offlineUserSubjectCrossRefRepository.insert(it)}
            onlineSubjects.forEach { offlineSubjectRepository.insertSubject(it)}

            val userId = loggedInUser!!.id
            val userSubjectCrossRefs = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForUser(userId)

            val subjectIds = userSubjectCrossRefs.map { it.subjectId }
            val subjects = offlineSubjectRepository.getActiveSubjectsByIds(subjectIds)
            val sortedSubjects = subjects.sortedBy { it.code }

            val subjectCodesNames = sortedSubjects.map { "${it.code} - ${it.name}" }

            if (subjectCodesNames.isNotEmpty()) {
                _subjects.value = listOf("All") + subjectCodesNames
            } else {
                _subjects.value = emptyList()
            }
        }
    }

    suspend fun filterAttendance(subjectCodes: List<String>, selectedSubject: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
            val filteredAttendances = if (selectedSubject == "All") {
                offlineAttendanceRepository.filterAttendancesBySubjectIdsAndDateRange(subjectCodes, startDate, endDate)
            } else {
                offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(startDate, endDate, selectedSubject)
            }

            filteredAttendances.collect { attendances ->
                _attendances.value = attendances.sortedByDescending { attendance ->
                    LocalDate.parse(attendance.date, formatter)
                        .atTime(java.time.LocalTime.parse(attendance.time, DateTimeFormatter.ofPattern("hh:mm a")))
                }
            }
        }
    }
}