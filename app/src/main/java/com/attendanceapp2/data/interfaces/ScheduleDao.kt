package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.subject.Schedule

@Dao
interface ScheduleDao {
    @Query("DELETE FROM `Subject Schedules`")
    suspend fun deleteAllSchedules()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule)

    @Update
    suspend fun update(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)

    @Query("SELECT * FROM `Subject Schedules` WHERE subjectId = :subjectId")
    suspend fun getSchedulesForSubject(subjectId: Int): List<Schedule>
}