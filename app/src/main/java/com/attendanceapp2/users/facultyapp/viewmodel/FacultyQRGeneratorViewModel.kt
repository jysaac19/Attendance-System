package com.attendanceapp2.users.facultyapp.viewmodel

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.user.UserRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.EnumMap

data class QRCode (
    val subjectCode : String,
    val subjectName : String,
    val date : String
)
class QRGeneratorViewModel(
    private val subjectRepo: SubjectRepository
) : ViewModel() {

    fun qrCodeGenerator(data: String): Bitmap? {
        if (data.isBlank()) {
            return null
        }

        val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb())

                }
            }
            return bmp
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}