package com.attendanceapp2.data.model.attendance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ScannedQRCode (
    val subjectId: Long,
    val subjectName: String,
    val subjectCode: String,
    val date : String,
    val time : String
)

object ScannedQRCodeHolder {
    private val _scannedQRCodeHolder = MutableStateFlow<ScannedQRCode?>(null)
    val scannedQRCodeHolder = _scannedQRCodeHolder.asStateFlow()

    fun setScannedQRCode(qrCode: ScannedQRCode) {
        _scannedQRCodeHolder.value = qrCode
    }

    fun clearScannedQRCode() {
        _scannedQRCodeHolder.value = null
    }

    fun getScannedQRCode(): ScannedQRCode? {
        return _scannedQRCodeHolder.value
    }
}