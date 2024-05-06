package attendanceappusers.studentapp.viewmodel

import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.attendanceapp2.data.model.attendance.ScannedQRCode
import com.attendanceapp2.data.model.attendance.ScannedQRCodeHolder
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.ByteBuffer


@OptIn(DelicateCoroutinesApi::class)
class QRCodeAnalyzer(
    private val scannerViewModel: ScannerViewModel,
    private val onQRCodeScanned : (ScannedQRCode) -> Unit
) : ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf (
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
        ImageFormat.FLEX_RGBA_8888,
        ImageFormat.FLEX_RGB_888
    )

    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats) {
            val bytes = image.planes.first().buffer.toByteArray()

            val source = PlanarYUVLuminanceSource (
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )

            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE,
                                //optional adding
                            )
                        )
                    )
                }.decode(binaryBitmap)

                val scannedQRCode = Gson().fromJson(result.text, ScannedQRCode::class.java)
                ScannedQRCodeHolder.setScannedQRCode(scannedQRCode)
                onQRCodeScanned(scannedQRCode)

                // Call validateAndInsertAttendance function here
                GlobalScope.launch {
                    val result = scannerViewModel.validateAndInsertAttendance()
                    when(result) {
                        is AttendanceResult.Success -> Log.i("QRCodeAnalyzer", result.message)
                        is AttendanceResult.Error -> Log.e("QRCodeAnalyzer", result.errorMessage)
                    }
                }
            } catch ( e:Exception ) {
                e.printStackTrace()
            } finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray() : ByteArray {
        rewind()
        return ByteArray(remaining()).also{
            get(it)
        }
    }
}