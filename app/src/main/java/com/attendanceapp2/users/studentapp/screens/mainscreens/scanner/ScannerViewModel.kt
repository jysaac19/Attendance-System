package com.attendanceapp2.users.studentapp.screens.mainscreens.scanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.universaldata.ScannedQRCode
import com.attendanceapp2.universaldata.ScannedQRCodeHolder

class ScannerViewModel(
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {
    var code by mutableStateOf("")
    val scannedQRCode = ScannedQRCodeHolder.getScannedQRCode()

    
}