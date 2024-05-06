package attendanceappusers.facultyapp.screens.mainscreen.qrscreen

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.attendance.QRCode
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.time.LocalDate
import java.time.LocalTime
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
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"))
        val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))
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