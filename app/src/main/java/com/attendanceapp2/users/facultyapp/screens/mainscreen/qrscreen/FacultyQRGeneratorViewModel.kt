package com.attendanceapp2.users.facultyapp.screens.mainscreen.qrscreen

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.attendanceapp2.universaldata.QRCode
import com.attendanceapp2.universaldata.SelectedSubject
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
     * Generate a QR code bitmap for the selected subject with current date and time.
     *
     * @param selectedSubject The selected subject.
     * @return The generated QR code bitmap.
     */
    fun generateQrCodeBitmap(selectedSubject: SelectedSubject): Bitmap? {
        val (date, time) = getCurrentTimeAndDateInPhilippines()
        val qrData = QRCode(
            subjectId = selectedSubject.id,
            subjectName = selectedSubject.name,
            subjectCode = selectedSubject.code,
            date = date,
            time = time
        )
        return qrCodeGenerator(qrData)
    }

    /**
     * Get the current time and date in the Philippines timezone in "MMM dd, yyyy" and "hh:mm a" format.
     *
     * @return Pair of date and time strings.
     */
    private fun getCurrentTimeAndDateInPhilippines(): Pair<String, String> {
        val zoneId = ZoneId.of("Asia/Manila")
        val dateTimeFormatterDate = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val dateTimeFormatterTime = DateTimeFormatter.ofPattern("hh:mm a")
        val date = ZonedDateTime.now(zoneId).format(dateTimeFormatterDate)
        val time = ZonedDateTime.now(zoneId).format(dateTimeFormatterTime)
        return Pair(date, time)
    }

    /**
     * Generate a QR code bitmap for the given data string.
     *
     * @param qrCode The QR code data.
     * @return The generated QR code bitmap.
     */
    private fun qrCodeGenerator(qrCode: QRCode): Bitmap? {
        val gson = Gson()
        val json = gson.toJson(qrCode)
        if (json.isBlank()) {
            return null
        }

        val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(json, BarcodeFormat.QR_CODE, 600, 600, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
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