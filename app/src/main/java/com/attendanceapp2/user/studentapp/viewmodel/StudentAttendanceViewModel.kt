package com.attendanceapp2.user.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.universal.viewmodel.AttendanceViewModel
import kotlinx.coroutines.flow.Flow

class StudentAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val attendanceViewModel: AttendanceViewModel
) : ViewModel() {
    fun getAttendancesByLoggedInUser(userId: Long) : Flow<List<Attendance>> {

        return offlineAttendanceRepository.getAttendancesByUserId(userId)
    }

    fun filterAttendance(startDate: String, endDate: String, userId: Long, selectedSubject: String): Flow<List<Attendance>>{

        if(selectedSubject == "All"){
            return offlineAttendanceRepository.getAttendancesByUserId(userId)
        }

        return offlineAttendanceRepository.filterAttendance(startDate, endDate, userId, selectedSubject)
    }

}