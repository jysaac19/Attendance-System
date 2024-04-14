package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.model.Attendance

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: Attendance)

    suspend fun updateAttendance(attendance : Attendance)

    suspend fun deleteAttendance(attendance : Attendance)

    suspend fun getAttendancesBySubjectId(subjectId: Long): List<Attendance>

    suspend fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId: Long, date: String): List<Attendance>

}