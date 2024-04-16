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
) : AttendanceRepository {
    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            attendances.forEach { attendance -> attendanceDao.insert(attendance) }
        }
    }
    override suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)

    override suspend fun updateAttendance(attendance: Attendance) = attendanceDao.update(attendance)

    override suspend fun deleteAttendance(attendance: Attendance) = attendanceDao.delete(attendance)

    override fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId: Long, date: String): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserIdSubjectIdAndDate(userId, subjectId, date)
    }
    override fun filterAttendance(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendance(startDate, endDate, userId, subjectCode)
    }

    override fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserId(userId)
    }

    override fun getAttendancesByUserIds(userIds: List<Long>): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserIds(userIds)
    }

    override fun getAttendancesBySubjectIds(subjectIds: List<Long>): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesBySubjectIds(subjectIds)
    }

    override fun getAttendancesByUserIdAndSubjectId(userId: Long, subjectId: Long): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserIdAndSubjectId(userId, subjectId)
    }
}