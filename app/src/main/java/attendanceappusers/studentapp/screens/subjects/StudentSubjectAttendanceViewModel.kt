package attendanceappusers.studentapp.screens.subjects

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentSubjectAttendanceViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

    private val _attendances = MutableStateFlow<List<Attendance>>(emptyList())
    val attendances = _attendances.asStateFlow()

    suspend fun filterStudentSubjectAttendancesByDateRange(startDate: String, endDate: String) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()

        offlineAttendanceRepository.filterStudentAttendanceBySubjectCodeAndDateRange(
            startDate = startDate,
            endDate = endDate,
            userId = loggedInUser!!.id,
            subjectCode = selectedSubject!!.code
        ).collect { attendances ->
            _attendances.value = attendances
            println(attendances)
        }
    }
}