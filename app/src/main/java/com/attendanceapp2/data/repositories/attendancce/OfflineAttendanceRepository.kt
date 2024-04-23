package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.model.Attendance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineAttendanceRepository(
    private val attendanceDao: AttendanceDao,
    private val attendances: List<Attendance>
) {
    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            attendances.forEach { attendance -> attendanceDao.insert(attendance) }
        }
    }
    suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)

    suspend fun updateAttendance(attendance: Attendance) = attendanceDao.update(attendance)

    suspend fun deleteAttendance(attendance: Attendance) = attendanceDao.delete(attendance)

    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId: Long, date: String): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserIdSubjectIdAndDate(userId, subjectId, date)
    }
    fun filterAttendance(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendance(startDate, endDate, userId, subjectCode)
    }

    fun filterFacultyAttendance(startDate: String, endDate: String, subjectCode: String): Flow<List<Attendance>> {
        return attendanceDao.filterFacultyAttendance(startDate, endDate, subjectCode)
    }

    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserId(userId)
    }

    fun getAllAttendances() : Flow<List<Attendance>> {
        return attendanceDao.getAttendances()
    }
    fun getAttendancesBySubjectIds(subjectIds: List<Long>) : Flow<List<Attendance>> {
        return attendanceDao.getAttendancesBySubjectIds(subjectIds)
    }
}