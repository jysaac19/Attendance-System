package com.attendanceapp2.users.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.universal.data.LoggedInUserHolder
import com.attendanceapp2.universal.data.ScannedQRCodeHolder
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

sealed class AttendanceResult {
    data class Success(val message: String) : AttendanceResult()
    data class Error(val errorMessage: String) : AttendanceResult()
}

class ScannerViewModel(
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {

    suspend fun validateAndInsertAttendance(): AttendanceResult {
        val currentDate = getCurrentDateInPhilippines()
        val currentTime = getCurrentTimeInPhilippines()

        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        val scannedQRCode = ScannedQRCodeHolder.getScannedQRCode()

        if (loggedInUser == null || scannedQRCode == null) {
            return AttendanceResult.Error("User or QR code information is missing.")
        }

        // Validate expiration date
        if (scannedQRCode.date != currentDate) {
            return AttendanceResult.Error("QR code has expired.")
        }

        // Validate time
        if (!isValidTime(scannedQRCode.time, currentTime)) {
            return AttendanceResult.Error("QR code scan time exceeds 5 minutes.")
        }

        // Check if the user already has attendance
        val existingAttendancesList = attendanceRepo.getAttendancesByUserIdSubjectIdAndDate(
            loggedInUser.userId,
            scannedQRCode.subjectId,
            currentDate
        ).firstOrNull() // Collect the flow to get the value synchronously or null if the flow is empty

        if (!existingAttendancesList.isNullOrEmpty()) {
            return AttendanceResult.Error("Attendance Recorded Successfully")
        }

        // Insert attendance
        val attendance = Attendance(
            userId = loggedInUser.userId,
            firstname = loggedInUser.firstname,
            lastname = loggedInUser.lastname,
            subjectId = scannedQRCode.subjectId,
            subjectName = scannedQRCode.subjectName,
            subjectCode = scannedQRCode.subjectCode,
            date = currentDate,
            time = currentTime
        )

        attendanceRepo.insertAttendance(attendance)

        return AttendanceResult.Success("Attendance recorded successfully.")
    }

    private fun isValidTime(qrTime: String, currentTime: String): Boolean {
        val qrFormattedTime = SimpleDateFormat("hh:mm a").parse(qrTime)
        val currentFormattedTime = SimpleDateFormat("hh:mm a").parse(currentTime)

        val differenceInMillis = currentFormattedTime.time - qrFormattedTime.time
        val differenceInMinutes = differenceInMillis / (1000 * 60)

        return differenceInMinutes <= 5
    }

    private fun getCurrentDateInPhilippines(): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Manila")
        return dateFormat.format(Date())
    }

    private fun getCurrentTimeInPhilippines(): String {
        val timeFormat = SimpleDateFormat("hh:mm a")
        timeFormat.timeZone = TimeZone.getTimeZone("Asia/Manila")
        return timeFormat.format(Date())
    }
}