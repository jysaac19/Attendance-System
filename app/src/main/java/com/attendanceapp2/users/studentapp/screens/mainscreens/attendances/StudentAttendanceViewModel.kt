package com.attendanceapp2.users.studentapp.screens.mainscreens.attendances

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import kotlinx.coroutines.flow.Flow

class StudentAttendanceViewModel(
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {
    fun getAttendancesByLoggedInUser(userId: Long) : Flow<List<Attendance>> {

        return attendanceRepository.getAttendancesByUserId(userId)
    }

    fun filterAttendance(startDate: String, endDate: String, userId: Long, selectedSubject: String): Flow<List<Attendance>>{

        if(selectedSubject == "All"){
            return attendanceRepository.getAttendancesByUserId(userId)
        }

        return attendanceRepository.filterAttendance(startDate, endDate, userId, selectedSubject)
    }

}