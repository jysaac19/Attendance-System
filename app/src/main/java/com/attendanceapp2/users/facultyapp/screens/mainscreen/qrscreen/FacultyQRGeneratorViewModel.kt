package com.attendanceapp2.users.facultyapp.screens.mainscreen.qrscreen

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.universaldata.QRCode
import com.attendanceapp2.universaldata.SelectedSubject
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
    fun generateQrCodeBitmap(selectedSubject: SelectedSubject): Bitmap? {
        val qrData = QRCode(
            subjectId = selectedSubject.id,
            subjectName = selectedSubject.name,
            subjectCode = selectedSubject.code,
            dynamicFactor = getCurrentTimeAndDateInPhilippines() // Include dynamic factor (current time and date)
        )
        return qrCodeGenerator(qrData)
    }

    /**
     * Get the current time and date in the Philippines timezone in "hh:mm a, MMM dd, yyyy" format.
     *
     * @return The current time and date in "hh:mm a, MMM dd, yyyy" format.
     */
    private fun getCurrentTimeAndDateInPhilippines(): String {
        val zoneId = ZoneId.of("Asia/Manila")
        val dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a, MMM dd, yyyy")
        return ZonedDateTime.now(zoneId).format(dateTimeFormatter)
    }

    /**
     * Generate a QR code bitmap for the given data string.
     *
     * @param data The data string for the QR code.
     * @return The generated QR code bitmap.
     */
    private fun qrCodeGenerator(qrCode: QRCode): Bitmap? {
        val data = "${qrCode.subjectId},${qrCode.subjectName},${qrCode.subjectCode},${qrCode.dynamicFactor}"
        if (data.isBlank()) {
            return null
        }

        val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 600, 600, hints)
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