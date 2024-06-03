package com.attendanceapp2.data.model.attendance

data class AttendanceCounts(
    val userId: Int,
    val firstname: String?,
    val lastname: String?,
    val presentCount: Int,
    val absentCount: Int,
    val lateCount: Int,
    val totalCount: Int
)