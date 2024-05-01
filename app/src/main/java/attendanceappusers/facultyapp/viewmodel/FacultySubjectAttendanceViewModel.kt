package attendanceappusers.facultyapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.model.LoggedInUserHolder
import com.attendanceapp2.data.model.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            offlineAttendanceRepository.filterAttendance(startDate.toString(), endDate.toString(), subject.code).collect { attendances ->
                _facultySubjectAttendances.value = attendances
                Log.d("FacultySubjectAttendanceViewModel", "Faculty Subject Attendances: $attendances")
            }
        }
    }
}