package attendanceappusers.facultyapp.screens.mainscreen.subjects.facultysubjectattendances

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate


class FacultySubjectAttendanceViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

    private val _facultySubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val facultySubjectAttendances = _facultySubjectAttendances.asStateFlow()

    // Function to fetch student subject attendances using the userId of the loggedInUser and subjectId of the selectedSubject
    suspend fun fetchFacultySubjectAttendances(startDate: LocalDate, endDate: LocalDate) {
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        selectedSubject?.let { subject ->
            offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(startDate.toString(), endDate.toString(), subject.code).collect { attendances ->
                _facultySubjectAttendances.value = attendances
                println("Faculty Subject Attendances: $attendances")
            }
        }
    }
}