package com.attendanceapp2.user.facultyapp.screens.mainscreen.subjects.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository

class FacultySubjectAttendancesViewModel (
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

}