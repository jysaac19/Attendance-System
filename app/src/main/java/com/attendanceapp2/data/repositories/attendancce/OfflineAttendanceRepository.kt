package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Implementation of AttendanceRepository for offline mode
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

    override suspend fun getAttendancesBySubjectId(subjectId: Long): List<Attendance> {
        return attendanceDao.getAttendancesBySubjectId(subjectId)
    }

    override suspend fun getAttendancesBySubjectIdAndUserId(subjectId: Long, userId: Long, date: String): List<Attendance> {
        return attendanceDao.getAttendancesBySubjectIdAndUserId(subjectId, userId, date)
    }
}