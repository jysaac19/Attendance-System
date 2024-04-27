package studentapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.model.LoggedInUserHolder
import com.attendanceapp2.data.model.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class StudentSubjectAttendanceViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

    private val _studentSubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val studentSubjectAttendances = _studentSubjectAttendances.asStateFlow()

    // Function to fetch student subject attendances using the userId of the loggedInUser and subjectId of the selectedSubject
    suspend fun fetchStudentSubjectAttendances(startDate: LocalDate, endDate: LocalDate) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        loggedInUser?.let { user ->
            selectedSubject?.let { subject ->
                offlineAttendanceRepository.filterStudentAttendance(
                    startDate.toString(),
                    endDate.toString(),
                    user.userId,
                    subject.code
                ).collect { attendances ->
                    _studentSubjectAttendances.value = attendances
                    Log.d("StudentSubjectAttendanceViewModel", "Student Subject Attendances: $attendances")
                }
            }
        }
    }
}