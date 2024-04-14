package com.attendanceapp2.users.studentapp.screens.mainscreens.scanner

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.universaldata.LoggedInUserHolder
import com.attendanceapp2.universaldata.ScannedQRCode
import com.attendanceapp2.universaldata.ScannedQRCodeHolder
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

sealed class AttendanceResult {
    data class Failed(val reason: String) : AttendanceResult()
    object Successful : AttendanceResult()
}

// ViewModel for the QR code scanner screen
class ScannerViewModel(
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {
    var code by mutableStateOf("")

    suspend fun validateScannedQRCode(): AttendanceResult {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val scannedQRCode = ScannedQRCodeHolder.getScannedQRCode()
        val currentDate = ZonedDateTime.now(ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        val currentTime = ZonedDateTime.now(ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("hh:mm a"))

        // Check if scannedQRCode is null
        if (scannedQRCode == null) {
            return AttendanceResult.Failed("Invalid QR Code: Data is missing")
        }

        // Check if the QR code date matches the current date
        if (scannedQRCode.date != currentDate) {
            return AttendanceResult.Failed("Invalid QR Code: Date does not match")
        }

        // Check if the QR code time is within 5 minutes before the current time
        val qrCodeTime = ZonedDateTime.parse(scannedQRCode.time, DateTimeFormatter.ofPattern("hh:mm a"))
        val fiveMinutesAgo = ZonedDateTime.now(ZoneId.of("Asia/Manila")).minusMinutes(5)
        if (qrCodeTime.isBefore(fiveMinutesAgo)) {
            return AttendanceResult.Failed("Invalid QR Code: Time is too old")
        }

        // Check if the user already has an attendance for the selected subject and current date
        val existingAttendance = attendanceRepo.getAttendancesBySubjectIdAndUserId(
            scannedQRCode.subjectId,
            loggedInUser?.userId ?: 0,
            currentDate
        )
        if (existingAttendance.isNotEmpty()) {
            return AttendanceResult.Failed("Already have an Attendance")
        }

        // All checks passed, attendance is successful
        val attendance = Attendance(
            id = 0, // auto-generated ID
            userId = loggedInUser?.userId ?: 0,
            firstname = loggedInUser?.firstname ?: "",
            lastname = loggedInUser?.lastname ?: "",
            subjectId = scannedQRCode.subjectId,
            subjectName = scannedQRCode.subjectName,
            subjectCode = scannedQRCode.subjectCode,
            date = currentDate,
            time = currentTime
        )

        attendanceRepo.insertAttendance(attendance)
        Log.d("Attendance Successful", "Attendance: $attendance")
        return AttendanceResult.Successful
    }
}