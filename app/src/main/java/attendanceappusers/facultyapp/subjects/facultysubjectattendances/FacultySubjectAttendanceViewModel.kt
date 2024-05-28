package attendanceappusers.facultyapp.subjects.facultysubjectattendances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class FacultySubjectAttendanceViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository
) : ViewModel() {

    private val _facultySubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val facultySubjectAttendances = _facultySubjectAttendances.asStateFlow()

    init {
        updateOfflineAttendances()
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

    suspend fun fetchFacultySubjectAttendances(startDate: String, endDate: String) {
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        viewModelScope.launch {
            updateOfflineAttendances()

            selectedSubject?.let { subject ->
                offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(
                    startDate = startDate,
                    endDate = endDate,
                    subjectCode = subject.code
                ).collect { attendances ->
                    // Sort attendances by date and time (most recent first)
                    val sortedAttendances = attendances.sortedWith(compareByDescending<Attendance> { LocalDate.parse(it.date, DateTimeFormatter.ofPattern("MM-dd-yyyy")) }
                        .thenByDescending {
                            // Parse time with AM/PM marker
                            LocalTime.parse(it.time, DateTimeFormatter.ofPattern("hh:mm a"))
                        })

                    _facultySubjectAttendances.value = sortedAttendances
                    println("Faculty Subject Attendances: $sortedAttendances")
                }
            }
        }
    }
}