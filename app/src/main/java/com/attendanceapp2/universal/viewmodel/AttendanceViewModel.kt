package com.attendanceapp2.universal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.universal.data.LoggedInUserHolder
import com.attendanceapp2.universal.data.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AttendanceViewModel(
    private val attendanceRepo: AttendanceRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

    private val _studentAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    private val _facultyAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    private val _studentSubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    private val _facultySubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())

    val studentAttendances = _studentAttendances.asStateFlow()
    val facultyAttendances = _facultyAttendances.asStateFlow()
    val studentSubjectAttendances: StateFlow<List<Attendance>> = _studentSubjectAttendances
    val facultySubjectAttendances = _facultySubjectAttendances.asStateFlow()

    // Function to fetch student attendances using LoggedInUserId
    suspend fun fetchStudentAttendances() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            viewModelScope.launch {
                attendanceRepo.getAttendancesByUserId(user.userId).collect { attendances ->
                    _studentAttendances.value = attendances
//                    Log.d("AttendanceViewModel", "Student Attendances: $attendances")
                }
            }
        }
    }

    // Function to fetch faculty attendances using the list of subjectId of the fetchSubjectsForLoggedInUser
    suspend fun fetchFacultyAttendances() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            val subjectIds = subjectViewModel.getSubjectIds() // Call getSubjectIds() from SubjectViewModel
            viewModelScope.launch {
                attendanceRepo.getAttendancesBySubjectIds(subjectIds).collect { attendances ->
                    _facultyAttendances.value = attendances
                    Log.d("AttendanceViewModel", "Faculty Attendances: $attendances")
                }
            }
        }
    }

    // Function to fetch student subject attendances using the userId of the loggedInUser and subjectId of the selectedSubject
    suspend fun fetchStudentSubjectAttendances() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        loggedInUser?.let { user ->
            selectedSubject?.let { subject ->
                attendanceRepo.getAttendancesByUserIdAndSubjectId(user.userId, subject.id).collect { attendances ->
                    _studentSubjectAttendances.value = attendances
                    Log.d("AttendanceViewModel", "Student Subject Attendances: $attendances")
                }
            }
        }
    }

    // Function to fetch faculty subject attendances using the subjectId of the selectedSubject
    suspend fun fetchFacultySubjectAttendances() {
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        selectedSubject?.let { subject ->
            viewModelScope.launch {
                val attendances = attendanceRepo.getAttendancesBySubjectIds(listOf(subject.id)).collect { attendances ->
                    _facultySubjectAttendances.value = attendances
                    Log.d("AttendanceViewModel", "Faculty Subject Attendances: $attendances")
                }
            }
        }
    }
}