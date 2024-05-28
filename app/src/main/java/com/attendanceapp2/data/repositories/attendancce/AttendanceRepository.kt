package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.model.attendance.Attendance
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: Attendance)

    suspend fun updateAttendance(attendance: Attendance)

    suspend fun deleteAttendance(attendance: Attendance)

    fun getAttendancesByUserIdSubjectIdAndDate(userId: Int, subjectId: Int, date: String): Flow<List<Attendance>>

    fun filterAttendance(startDate: String, endDate: String, userId: Int, subjectCode: String): Flow<List<Attendance>>

    fun getAttendancesByUserId(userId: Int): Flow<List<Attendance>>

    fun getAttendancesByUserIds(userIds: List<Int>): Flow<List<Attendance>>

    fun getAttendancesBySubjectIds(subjectIds: List<Int>): Flow<List<Attendance>>

    fun getAttendancesByUserIdAndSubjectId(userId: Int, subjectId: Int): Flow<List<Attendance>>
}