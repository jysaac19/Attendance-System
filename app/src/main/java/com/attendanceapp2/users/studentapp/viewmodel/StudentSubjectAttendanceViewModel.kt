package com.attendanceapp2.users.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.universal.data.LoggedInUserHolder
import com.attendanceapp2.universal.data.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StudentSubjectAttendanceViewModel (
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {

    private val _attendanceList = MutableStateFlow<List<Attendance>>(emptyList())
    val attendanceList: MutableStateFlow<List<Attendance>> = _attendanceList

    fun fetchAttendanceByLoggedInUserAndSelectedSubject() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val selectedSubject = SelectedSubjectHolder.getSelectedSubject()

        if (loggedInUser != null && selectedSubject != null) {
            val studentId = loggedInUser.userId
            val subjectId = selectedSubject.id

            viewModelScope.launch {
                attendanceRepo.getAttendancesByUserIdAndSubjectId(studentId, subjectId).collect {
                    _attendanceList.value = it
                }
            }
        }
    }
}