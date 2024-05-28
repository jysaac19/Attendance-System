package attendanceappusers.adminapp.homescreen.attendancemanagement.searchsubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder.selectedSubject
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.SelectedStudentHolder
import com.attendanceapp2.data.model.user.SelectedStudentHolder.selectedStudent
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchSubjectViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,

    private val onlineAttendanceRepository: OnlineAttendanceRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository
) : ViewModel() {
    private val _subjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects

    init {
        updateOfflineSubjects()
        updateOfflineAttendances()
    }

    fun searchSubjects(query: String): Flow<List<Subject>> {
        viewModelScope.launch {
            offlineSubjectRepository.searchSubject(query).collect { subjects ->
                _subjects.value = subjects
            }
        }
        return offlineSubjectRepository.searchSubject(query)
    }

    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            val subjects = onlineSubjectRepository.getAllSubjects()

            subjects.forEach {
                offlineSubjectRepository.insertSubject(it)
            }

            offlineSubjectRepository.getAllSubjects().collect() {
                _subjects.value = it
            }
        }
    }

    private fun updateOfflineAttendances() {
        viewModelScope.launch {
            val attendances = onlineAttendanceRepository.getAllAttendances()
            attendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
            }
        }
    }

    suspend fun insertAttendance(attendance: Attendance): Results.AddAttendanceResult {
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        val subject = SelectedSubjectHolder.getSelectedSubject()
        val student = SelectedStudentHolder.getSelectedStudent()

        // Check if both subject and student are selected
        if (subject == null || student == null) {
            return Results.AddAttendanceResult(failureMessage = "Subject or student not selected.")
        }

        // Check if the user already has attendance
        val existingAttendancesList = offlineAttendanceRepository.getAttendancesByUserIdSubjectIdAndDate(
            selectedStudent.value?.id ?: 0,
            selectedSubject.value?.id ?: 0,
            currentDate
        ).firstOrNull() // Collect the flow to get the value synchronously or null if the flow is empty

        if (!existingAttendancesList.isNullOrEmpty()) {
            return Results.AddAttendanceResult(failureMessage = "Attendance already recorded for today.")
        }

        // If no attendance exists for the current date, insert the new attendance
        onlineAttendanceRepository.addAttendance(attendance)
        return Results.AddAttendanceResult(successMessage = "Attendance added successfully.")
    }
}