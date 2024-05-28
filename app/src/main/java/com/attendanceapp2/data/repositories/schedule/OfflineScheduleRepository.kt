package com.attendanceapp2.data.repositories.schedule

import com.attendanceapp2.data.interfaces.ScheduleDao
import com.attendanceapp2.data.model.subject.Schedule

class OfflineScheduleRepository(
    private val scheduleDao: ScheduleDao
) {
    suspend fun deleteAllSchedules() {
        scheduleDao.deleteAllSchedules()
    }

    suspend fun insertSchedule(schedule: Schedule) = scheduleDao.insert(schedule)

    suspend fun updateSchedule(schedule: Schedule) = scheduleDao.update(schedule)

    suspend fun deleteSchedule(schedule: Schedule) = scheduleDao.delete(schedule)

    suspend fun getSchedulesForSubject(subjectId: Int): List<Schedule> {
        return scheduleDao.getSchedulesForSubject(subjectId)
    }
}