package com.attendanceapp2.user.facultyapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.universal.data.LoggedInUserHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class FacultyAttendanceViewModel(
    private val userSubjectCrossRefRepo: UserSubjectCrossRefRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    private val _facultyAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val facultyAttendances = _facultyAttendances.asStateFlow()

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
    suspend fun fetchFacultyAttendances(selectedSubject: String, startDate: LocalDate, endDate: LocalDate) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        // Retrieve subject IDs for the user
        loggedInUser?.let { user ->
            // Retrieve subject IDs for the user
            val subjectIds = userSubjectCrossRefRepo.getSubjectIdsForUser(user.userId)
            if (selectedSubject == "All") {
                // Fetch attendances for all subjects
                offlineAttendanceRepository.getAttendancesBySubjectIds(subjectIds).collect { attendances ->
                    _facultyAttendances.value = attendances
                    Log.d("FacultyAttendanceViewModel", "Student Subject Attendances: $attendances")
                }
            } else {
                // Fetch attendances for the selected subject
                offlineAttendanceRepository.filterAttendance(startDate.toString(), endDate.toString(), user.userId, selectedSubject).collect { attendances ->
                    _facultyAttendances.value = attendances
                    Log.d("FacultyAttendanceViewModel", "Student Subject Attendances: $attendances")
                }
            }
        }
    }
}