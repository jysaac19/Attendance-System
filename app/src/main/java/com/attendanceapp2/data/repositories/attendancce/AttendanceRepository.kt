package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.model.attendance.Attendance
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: Attendance)

    suspend fun updateAttendance(attendance: Attendance)

    suspend fun deleteAttendance(attendance: Attendance)

    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId: Long, date: String): Flow<List<Attendance>>

    fun filterAttendance(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>>

    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>>

    fun getAttendancesByUserIds(userIds: List<Long>): Flow<List<Attendance>>

    fun getAttendancesBySubjectIds(subjectIds: List<Long>): Flow<List<Attendance>>

    fun getAttendancesByUserIdAndSubjectId(userId: Long, subjectId: Long): Flow<List<Attendance>>
}