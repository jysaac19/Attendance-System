package com.attendanceapp2.users.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.universal.viewmodel.AttendanceViewModel
import kotlinx.coroutines.flow.Flow

class StudentAttendanceViewModel(
    private val attendanceRepo: AttendanceRepository,
    private val attendanceViewModel: AttendanceViewModel
) : ViewModel() {
    fun getAttendancesByLoggedInUser(userId: Long) : Flow<List<Attendance>> {

        return attendanceRepo.getAttendancesByUserId(userId)
    }

    fun filterAttendance(startDate: String, endDate: String, userId: Long, selectedSubject: String): Flow<List<Attendance>>{

        if(selectedSubject == "All"){
            return attendanceRepo.getAttendancesByUserId(userId)
        }

        return attendanceRepo.filterAttendance(startDate, endDate, userId, selectedSubject)
    }

}