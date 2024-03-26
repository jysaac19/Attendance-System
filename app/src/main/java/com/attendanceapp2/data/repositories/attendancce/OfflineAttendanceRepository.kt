package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.model.Attendance

class OfflineAttendanceRepository(private val attendanceDao: AttendanceDao) : AttendanceRepository {

    override suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)

    override suspend fun updateAttendance(attendance: Attendance) = attendanceDao.update(attendance)

    override suspend fun deleteAttendance(attendance: Attendance) = attendanceDao.delete(attendance)

}