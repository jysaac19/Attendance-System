package com.attendanceapp2.users.facultyapp.screens.mainscreen.qrscreen

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.attendanceapp2.universal.data.QRCode
import com.attendanceapp2.universal.data.SelectedSubject
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.EnumMap


/**
 * ViewModel for generating QR codes.
 */
class FacultyQRGeneratorViewModel : ViewModel() {

    /**
     * Generate a QR code bitmap for the selected subject with current time and date.
     *
     * @param selectedSubject The selected subject.
     * @return The generated QR code bitmap.
     */

    // Gson instance
    private val gson = Gson()

    fun generateQrCodeBitmap(selectedSubject: SelectedSubject): Bitmap? {
        val currentDate = getCurrentDateInPhilippines()
        val currentTime = getCurrentTimeInPhilippines()
        val qrData = QRCode(
            subjectId = selectedSubject.id,
            subjectName = selectedSubject.name,
            subjectCode = selectedSubject.code,
            date = currentDate,
            time = currentTime
        )

        // Convert QRCode object to JSON string
        val qrCodeJson = gson.toJson(qrData)

        return qrCodeGenerator(qrCodeJson)
    }
    /**
     * Get the current date in the Philippines timezone in "MMM dd, yyyy" format.
     *
     * @return The current date in "MMM dd, yyyy" format.
     */
    private fun getCurrentDateInPhilippines(): String {
        val zoneId = ZoneId.of("Asia/Manila")
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        return ZonedDateTime.now(zoneId).format(dateFormatter)
    }

    /**
     * Get the current time in the Philippines timezone in "hh:mm a" format.
     *
     * @return The current time in "hh:mm a" format.
     */
    private fun getCurrentTimeInPhilippines(): String {
        val zoneId = ZoneId.of("Asia/Manila")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        return ZonedDateTime.now(zoneId).format(timeFormatter)
    }
    /**
     * Generate a QR code bitmap for the given data string.
     *
     * @param data The data string for the QR code.
     * @return The generated QR code bitmap.
     */
    private fun qrCodeGenerator(qrCodeJson: String): Bitmap? {
        if (qrCodeJson.isBlank()) {
            return null
        }

        val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(qrCodeJson, BarcodeFormat.QR_CODE, 600, 600, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb()
                    )
                }
            }
            return bmp
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}