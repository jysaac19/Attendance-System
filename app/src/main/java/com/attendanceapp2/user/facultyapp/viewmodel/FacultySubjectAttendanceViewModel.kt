package com.attendanceapp2.user.facultyapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.universal.data.LoggedInUserHolder
import com.attendanceapp2.universal.data.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


class FacultySubjectAttendanceViewModel (
    private val userSubjectCrossRefRepo: UserSubjectCrossRefRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    private val _facultySubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val facultySubjectAttendances = _facultySubjectAttendances.asStateFlow()

    // Use MutableStateFlow for subjects
    private val _subjects = MutableStateFlow<List<String>>(emptyList())
    val subjects = _subjects.asStateFlow()

    init {
        // Fetch subjects for logged-in user
        fetchSubjectsForLoggedInUser()
    }

    // Function to fetch subjects associated with the logged-in user
    private fun fetchSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            viewModelScope.launch {
                val userId = user.userId
                // Retrieve subject IDs for the user
                val subjectIds = userSubjectCrossRefRepo.getSubjectIdsForUser(userId)
                if (subjectIds.isNotEmpty()) {
                    // Fetch subject codes using subject IDs
                    val subjects = offlineSubjectRepository.getSubjectsByIds(subjectIds)
                        .map { it.code } // Assuming subject has a property 'code' representing its code
                    _subjects.value = listOf("All") + subjects // Add "All" option to the beginning
                } else {
                    // No subjects found for the user
                    _subjects.value = emptyList()
                }
            }
        }
    }

    // Function to fetch student subject attendances using the userId of the loggedInUser and subjectId of the selectedSubject
    suspend fun fetchFacultySubjectAttendances(startDate: LocalDate, endDate: LocalDate) {
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        selectedSubject?.let { subject ->
            offlineAttendanceRepository.filterFacultyAttendance(startDate.toString(), endDate.toString(), subject.code).collect { attendances ->
                _facultySubjectAttendances.value = attendances
                Log.d("StudentSubjectAttendanceViewModel", "Student Subject Attendances: $attendances")
            }
        }
    }
}