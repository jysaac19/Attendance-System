package attendanceappusers.adminapp.subject.adminsubjectattendacne

import android.util.Log
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class AdminSubjectAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {
    val subjectInfo = SelectedSubjectHolder.getSelectedSubject()


    private val _adminSubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val adminSubjectAttendances = _adminSubjectAttendances.asStateFlow()


    // Function to fetch student subject attendances using the userId of the loggedInUser and subjectId of the selectedSubject
    suspend fun fetchAdminSubjectAttendances(startDate: String, endDate: String) {
        offlineAttendanceRepository.filterAttendancesBySubjectCodeAndDateRange(startDate, endDate,
            SelectedSubjectHolder.getSelectedSubject()?.code ?: "",
        ).collect { attendances ->
            _adminSubjectAttendances.value = attendances
            println("Student Subject Attendances: $attendances")
        }
    }
}