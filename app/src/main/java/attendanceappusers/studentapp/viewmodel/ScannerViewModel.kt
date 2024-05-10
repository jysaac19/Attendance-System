package attendanceappusers.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.attendance.ScannedQRCodeHolder
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

sealed class AttendanceResult {
    data class Success(val message: String) : AttendanceResult()
    data class Error(val errorMessage: String) : AttendanceResult()
}

class ScannerViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

    private suspend fun fetchAttendances() {
        // Fetch attendances for all subjects
        offlineAttendanceRepository.getAllAttendances().collect { attendances ->
//            Log.d("StudentSubjectAttendanceViewModel", "Student Subject Attendances: $attendances")
        }
    }

    suspend fun validateAndInsertAttendance(): AttendanceResult {
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))

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
        val existingAttendancesList = offlineAttendanceRepository.getAttendancesByUserIdSubjectIdAndDate(
            loggedInUser.userId,
            scannedQRCode.subjectId,
            currentDate
        ).firstOrNull() // Collect the flow to get the value synchronously or null if the flow is empty

        if (!existingAttendancesList.isNullOrEmpty()) {
            return AttendanceResult.Success("Attendance already recorded for today.")
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
            time = currentTime,
            status = "Present",
            usertype = loggedInUser.usertype
        )

        offlineAttendanceRepository.insertAttendance(attendance)

        // Fetch student attendances to update the list
        fetchAttendances()

        // Clear the scanned QR code
        ScannedQRCodeHolder.clearScannedQRCode()

        return AttendanceResult.Success("Attendance recorded successfully.")
    }


    private fun isValidTime(qrTime: String, currentTime: String): Boolean {
        val qrFormattedTime = SimpleDateFormat("hh:mm a").parse(qrTime)
        val currentFormattedTime = SimpleDateFormat("hh:mm a").parse(currentTime)

        val differenceInMillis = currentFormattedTime.time - qrFormattedTime.time
        val differenceInMinutes = differenceInMillis / (1000 * 60)

        return differenceInMinutes <= 5
    }
}