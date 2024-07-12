package attendanceappusers.adminapp.subject.adminsubjectattendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminSubjectAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository
) : ViewModel() {
    private val _adminSubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val adminSubjectAttendances = _adminSubjectAttendances.asStateFlow()

    suspend fun updateOfflineAttendances(startDate: String, endDate: String) {
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()
            val onlineAttendances = onlineAttendanceRepository.getAllAttendances()
            onlineAttendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
            }

            offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(
                startDate,
                endDate,
                SelectedSubjectHolder.getSelectedSubject()?.code ?: "",
            ).collect { attendances ->
                _adminSubjectAttendances.value = attendances
            }
        }
    }
}