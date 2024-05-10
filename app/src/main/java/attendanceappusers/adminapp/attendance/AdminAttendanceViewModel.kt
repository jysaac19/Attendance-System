package attendanceappusers.adminapp.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AdminAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    // List of attendances
    private val _attendances: MutableStateFlow<List<Attendance>> = MutableStateFlow(emptyList())
    val attendances: StateFlow<List<Attendance>> = _attendances

    fun filterAttendancesByAdmin(
        searchQuery: String,
        subjectCode: String,
        usertype: String,
        startDate: String,
        endDate: String
    ) {
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