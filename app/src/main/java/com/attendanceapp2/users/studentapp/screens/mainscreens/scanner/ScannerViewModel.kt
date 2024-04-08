package com.attendanceapp2.screens.mainscreens.scanner

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.student.StudentRepository

class ScannerViewModel(
    private val studentRepo : StudentRepository,
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {

    var code = ""

//    fun insertStudentAttendance(){
//       attendanceRepo.insertAttendance()
//    }
}