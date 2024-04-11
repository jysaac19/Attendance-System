package com.attendanceapp2.data.repositories.schedule

import com.attendanceapp2.data.model.Schedule

interface ScheduleRepository {

    suspend fun insertSchedule(schedule : Schedule)

    suspend fun updateSchedule(schedule : Schedule)

    suspend fun deleteSchedule(schedule : Schedule)

}