package com.attendanceapp2.users.studentapp.screens.mainscreens.scanner

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository

class ScannerViewModel(
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {

    var code = ""

//    fun insertStudentAttendance(){
//       attendanceRepo.insertAttendance()
//    }
}