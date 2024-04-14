package com.attendanceapp2.universaldata

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ScannedQRCode(
    val subjectId: Long,
    val subjectName: String,
    val subjectCode: String,
    val date: String,
    val time: String
)

object ScannedQRCodeHolder {
    private val _scannedQRCode = MutableStateFlow<ScannedQRCode?>(null)
    val scannedQRCode = _scannedQRCode.asStateFlow()

    fun setScannedQRCode(qrCode: ScannedQRCode) {
        _scannedQRCode.value = qrCode
    }

    fun clearScannedQRCode() {
        _scannedQRCode.value = null
    }

    fun getScannedQRCode(): ScannedQRCode? {
        return _scannedQRCode.value
    }
}