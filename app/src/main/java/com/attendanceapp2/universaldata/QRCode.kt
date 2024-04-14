package com.attendanceapp2.universaldata

data class QRCode(
    val subjectId: Long,
    val subjectName: String,
    val subjectCode: String,
    val dynamicFactor: String // Dynamic factor to add a changing element to the QR code
)