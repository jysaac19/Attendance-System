package com.attendanceapp2.users.facultyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository

class SubjectAttendanceViewModel (
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {

}