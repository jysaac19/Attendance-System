package attendanceappusers.adminapp.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository
) : ViewModel() {

    private val _attendances: MutableStateFlow<List<Attendance>> = MutableStateFlow(emptyList())
    val attendances: StateFlow<List<Attendance>> = _attendances

    private val _subjects = MutableStateFlow<List<String>>(listOf())
    val subjects: StateFlow<List<String>> = _subjects.asStateFlow()

    init {
        updateOfflineSubjects()
        updateOfflineAttendances()
    }

    fun filterAttendancesByAdmin(searchQuery: String, subjectCode: String, usertype: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            if (searchQuery.isEmpty() && subjectCode == "All" && usertype == "All") {
                offlineAttendanceRepository.filterAttendanceByDateRange(startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isEmpty() && subjectCode != "All" && usertype != "All") {
                offlineAttendanceRepository.filterAttendancesBySubjectCodeUserTypeAndDateRange(subjectCode, usertype, startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isEmpty() && subjectCode != "All" && usertype == "All") {
                offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(
                    startDate = startDate,
                    endDate = endDate,
                    subjectCode = subjectCode
                ).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isEmpty() && subjectCode == "All" && usertype != "All") {
                offlineAttendanceRepository.filterAttendancesByUserTypeAndDateRange(usertype, startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isNotEmpty() && subjectCode == "All" && usertype == "All") {
                offlineAttendanceRepository.filterAttendancesByQueryAndDateRange(searchQuery, startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isNotEmpty() && subjectCode != "All" && usertype != "All") {
                offlineAttendanceRepository.filterAttendancesByQuerySubjectCodeUserTypeAndDateRange(searchQuery, subjectCode, usertype, startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isNotEmpty() && subjectCode != "All" && usertype == "All") {
                offlineAttendanceRepository.filterAttendancesByQuerySubjectCodeAndDateRange(searchQuery, subjectCode,  startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            } else if (searchQuery.isNotEmpty() && subjectCode == "All" && usertype != "All") {
                offlineAttendanceRepository.filterAttendancesByQueryUserTypeAndDateRange(searchQuery, usertype,  startDate, endDate).collect { attendances ->
                    _attendances.value = attendances
                }
            }
        }
    }

    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val subjects = onlineSubjectRepository.getAllSubjects()

            // Sort the subjects by SubjectCode
            val sortedSubjects = subjects.sortedBy { it.code }

            // Map the sorted subjects to the desired format
            val subjectCodesNames = sortedSubjects.map { "${it.code} - ${it.name}" }

            // Update the _subjects value with the sorted list
            _subjects.value = listOf("All") + subjectCodesNames

            // Insert the sorted subjects into the offline repository
            sortedSubjects.forEach {
                offlineSubjectRepository.insertSubject(it)
            }
        }
    }

    private fun updateOfflineAttendances() {
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()
            val attendances = onlineAttendanceRepository.getAllAttendances()
            attendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
            }
        }
    }
}