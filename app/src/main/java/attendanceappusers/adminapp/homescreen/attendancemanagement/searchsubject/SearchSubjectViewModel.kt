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
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchSubjectViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {
    private val _subjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects

    init {
        fetchSubjects()
    }

    fun searchSubjects(query: String): Flow<List<Subject>> {
        viewModelScope.launch {
            offlineSubjectRepository.searchSubject(query).collect { subjects ->
                _subjects.value = subjects
            }
        }
        return offlineSubjectRepository.searchSubject(query)
    }

    private fun fetchSubjects() {
        viewModelScope.launch {
            // Call the repository function to get all attendances
            offlineSubjectRepository.getAllSubjects().collect() { subjects ->
                // Update the StateFlow with the fetched attendances
                _subjects.value = subjects
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
        offlineAttendanceRepository.insertAttendance(attendance)
        return Results.AddAttendanceResult(successMessage = "Attendance added successfully.")
    }
}