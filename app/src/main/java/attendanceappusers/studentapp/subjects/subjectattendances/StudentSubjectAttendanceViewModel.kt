package attendanceappusers.studentapp.subjects.subjectattendances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StudentSubjectAttendanceViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository
) : ViewModel() {

    private val _attendances = MutableStateFlow<List<Attendance>>(emptyList())
    val attendances = _attendances.asStateFlow()

    private fun updateOfflineAttendances() {
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()

            val onlineAttendances = onlineAttendanceRepository.getAllAttendances()
            onlineAttendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
            }
        }
    }

    fun filterStudentSubjectAttendancesByDateRange(startDate: String, endDate: String) {
        viewModelScope.launch {
            val loggedInUser = LoggedInUserHolder.getLoggedInUser()
            val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
            updateOfflineAttendances()
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

            val filteredAttendancesFlow = offlineAttendanceRepository.filterStudentAttendanceBySubjectCodeAndDateRange(
                userId = loggedInUser!!.id,
                subjectCode = selectedSubject!!.code,
                startDate = startDate,
                endDate = endDate
            )

            filteredAttendancesFlow.collect { attendances ->
                _attendances.value = attendances.sortedByDescending { attendance ->
                    LocalDate.parse(attendance.date, formatter)
                        .atTime(java.time.LocalTime.parse(attendance.time, DateTimeFormatter.ofPattern("hh:mm a")))
                }
            }
        }
    }
}