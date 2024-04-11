package com.attendanceapp2.data.repositories.schedule

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.interfaces.ScheduleDao
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.Schedule

class OfflineScheduleRepository(private val scheduleDao: ScheduleDao) : ScheduleRepository {

    override suspend fun insertSchedule(schedule: Schedule) = scheduleDao.insert(schedule)

    override suspend fun updateSchedule(schedule: Schedule) = scheduleDao.update(schedule)

    override suspend fun deleteSchedule(schedule: Schedule) = scheduleDao.delete(schedule)

}