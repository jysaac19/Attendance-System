package com.attendanceapp2.data.model.attendance

data class QRCode(
    val subjectId: Long,
    val subjectName: String,
    val subjectCode: String,
    val date : String,
    val time : String
)