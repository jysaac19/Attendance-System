package attendanceappusers.studentapp.attendance

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
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StudentAttendanceViewModel(
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
        updateOfflineUserSubCrossRefs()
        updateOfflineSubjects()
        updateOfflineAttendances()
    }

    private fun updateOfflineUserSubCrossRefs() {
        viewModelScope.launch {
            offlineUserSubjectCrossRefRepository.deleteAllUserSubjectCrossRefs()

            val onlineUserSubjectCrossRefs = onlineUserSubjectCrossRefRepository.getAllUserSubCrossRef()
            onlineUserSubjectCrossRefs.forEach {
                offlineUserSubjectCrossRefRepository.insert(it)
                Timber.d(it.toString())
            }
        }
    }

    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val subjects = onlineSubjectRepository.getAllSubjects()
            subjects.forEach {
                offlineSubjectRepository.insertSubject(it)
                Timber.d(it.toString())
            }
        }
    }

    private fun updateOfflineAttendances() {
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()
            val attendances = onlineAttendanceRepository.getAllAttendances()
            attendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
                Timber.d(it.toString())
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
            val subjectCodes = subjects.map { it.code }

            if (subjectCodes.isNotEmpty()) {
                _subjects.value = listOf("All") + subjectCodes
            } else {
                _subjects.value = emptyList()
            }
        }
    }

    fun filterStudentAttendances(subjectCode: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            val loggedInUser = LoggedInUserHolder.getLoggedInUser()
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
            val filteredAttendances = if (subjectCode == "All") {
                offlineAttendanceRepository.filterStudentAttendanceByDateRange(
                    userId = loggedInUser!!.id,
                    startDate = startDate,
                    endDate = endDate
                )
            } else {
                offlineAttendanceRepository.filterStudentAttendanceBySubjectCodeAndDateRange(
                    userId = loggedInUser!!.id,
                    subjectCode = subjectCode,
                    startDate = startDate,
                    endDate = endDate
                )
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

