package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.User
import com.attendanceapp2.universaldata.SelectedSubject
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

    override fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserId(userId)
    }

    override fun filterAttendance(startDate: String, endDate: String, userId: Long, selectedSubject: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendance(startDate, endDate, userId, selectedSubject)
    }
}