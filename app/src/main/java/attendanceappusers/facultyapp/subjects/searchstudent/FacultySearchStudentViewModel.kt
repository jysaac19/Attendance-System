package attendanceappusers.facultyapp.subjects.searchstudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.data.model.user.SelectedStudentHolder
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FacultySearchStudentViewModel(
    private val onlineUserRepository: OnlineUserRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,

    private val offlineUserRepository: OfflineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
) : ViewModel() {
    private val _students: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val students: StateFlow<List<User>> = _students

    private fun updateOfflineUserSubjectCrossRef() {
        viewModelScope.launch {
            offlineUserSubjectCrossRefRepository.deleteAllUserSubjectCrossRefs()
            val userSubjectCrossRef = onlineUserSubjectCrossRefRepository.getAllUserSubCrossRef()
            userSubjectCrossRef.forEach {
                offlineUserSubjectCrossRefRepository.insert(it)
            }
        }
    }

    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val subjects = onlineSubjectRepository.getAllSubjects()
            subjects.forEach {
                offlineSubjectRepository.insertSubject(it)
            }
        }
    }

    private fun updateOfflineAttendances() {
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()
            val attendances = onlineAttendanceRepository.getAllAttendances()
            attendances.forEach {
                offlineAttendanceRepository.insertAttendance(it)
            }
        }
    }

    private fun updateOfflineUsers() {
        viewModelScope.launch {
            offlineUserRepository.deleteAllUsers()
            val users = onlineUserRepository.getAllUsers()
            users.forEach {
                offlineUserRepository.insertUser(it)
            }

            val subject = SelectedSubjectHolder.getSelectedSubject()
            val userSubCrossRefs = offlineUserSubjectCrossRefRepository.getUserSubjectCrossRefBySubject(subject!!.id)
            val studentIds = userSubCrossRefs.map { it.userId }
            val students = offlineUserRepository.getUsersByIds(studentIds)
            students.forEach {
                Timber.d("Student: $it")
            }
            _students.value = students
        }
    }

    fun searchStudents(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                updateOfflineUserSubjectCrossRef()
                updateOfflineSubjects()
                updateOfflineUsers()

            } else {
                offlineUserRepository.searchUser(query).collect { students ->
                    _students.value = students
                }
            }
        }
    }

    suspend fun insertAttendance(attendance: Attendance): Results.AddAttendanceResult {
        updateOfflineAttendances()
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        val subject = SelectedSubjectHolder.getSelectedSubject()
        val student = SelectedStudentHolder.getSelectedStudent()

        // Check if both subject and student are selected
        if (subject == null || student == null) {
            return Results.AddAttendanceResult(failureMessage = "Subject or student not selected.")
        }

        // Check if the user already has attendance
        val existingAttendancesList = offlineAttendanceRepository.getAttendancesByUserIdSubjectIdAndDate(
            SelectedStudentHolder.selectedStudent.value?.id ?: 0,
            SelectedSubjectHolder.selectedSubject.value?.id ?: 0,
            currentDate
        ).firstOrNull() // Collect the flow to get the value synchronously or null if the flow is empty

        if (!existingAttendancesList.isNullOrEmpty()) {
            return Results.AddAttendanceResult(failureMessage = "Attendance already recorded for today.")
        }

        // If no attendance exists for the current date, insert the new attendance
        onlineAttendanceRepository.addAttendance(attendance)
        updateOfflineAttendances()
        return Results.AddAttendanceResult(successMessage = "Attendance added successfully.")
    }
}