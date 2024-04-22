package com.attendanceapp2.user.studentapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.universal.data.LoggedInUserHolder
import com.attendanceapp2.universal.data.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate

class StudentSubjectAttendanceViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

    private val _studentSubjectAttendances = MutableStateFlow<List<Attendance>>(emptyList())
    val studentSubjectAttendances = _studentSubjectAttendances.asStateFlow()

    // Store the selected start and end dates as MutableStateFlow
    private val _startDate = MutableStateFlow<LocalDate?>(null)
    val startDate: StateFlow<LocalDate?> = _startDate

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    val endDate: StateFlow<LocalDate?> = _endDate

    init {
        // Trigger filtering whenever startDate or endDate changes
        viewModelScope.launch {
            combine(_startDate, _endDate) { startDate, endDate ->
                Pair(startDate, endDate)
            }.distinctUntilChanged().collect { (startDate, endDate) ->
                filterAttendances(startDate, endDate)
            }
        }
    }

    // Function to fetch student subject attendances using the userId of the loggedInUser and subjectId of the selectedSubject
    suspend fun fetchStudentSubjectAttendances() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()
        loggedInUser?.let { user ->
            selectedSubject?.let { subject ->
                offlineAttendanceRepository.getAttendancesByUserIdAndSubjectId(user.userId, subject.id).collect { attendances ->
                    _studentSubjectAttendances.value = attendances
                    Log.d("StudentSubjectAttendanceViewModel", "Student Subject Attendances: $attendances")
                }
            }
        }
    }

    // Function to set the start date
    fun setStartDate(date: LocalDate?) {
        _startDate.value = date
    }

    fun setEndDate(date: LocalDate?) {
        _endDate.value = date
    }

    // Function to filter attendances based on the selected start and end dates
    private fun filterAttendances(startDate: LocalDate?, endDate: LocalDate?) {
        val attendances = _studentSubjectAttendances.value
        if (startDate != null && endDate != null) {
            val filteredAttendances = attendances.filter { attendance ->
                val attendanceDate = LocalDate.parse(attendance.date)
                attendanceDate >= startDate && attendanceDate <= endDate
            }
            _studentSubjectAttendances.value = filteredAttendances
        } else {
            // If startDate or endDate is null, return all attendances
            _studentSubjectAttendances.value = attendances
        }
    }
}