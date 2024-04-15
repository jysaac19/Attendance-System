package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.model.Attendance
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: Attendance)

    suspend fun updateAttendance(attendance : Attendance)

    suspend fun deleteAttendance(attendance : Attendance)

    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>>


    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId: Long, date: String): Flow<List<Attendance>>


    fun filterAttendance(startDate: String, endDate: String, userId: Long, selectedSubject: String): Flow<List<Attendance>>

}