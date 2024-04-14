package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.Attendance

// Data Access Object (DAO) for attendance-related operations
@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(attendance: Attendance)

    @Update
    suspend fun update(attendance: Attendance)

    @Delete
    suspend fun delete(attendance: Attendance)
    @Query("SELECT * FROM Attendance WHERE subjectId = :subjectId")
    suspend fun getAttendancesBySubjectId(subjectId: Long): List<Attendance>

    @Query("SELECT * FROM Attendance WHERE subjectId = :subjectId AND userId = :userId AND date = :date")
    suspend fun getAttendancesBySubjectIdAndUserId(subjectId: Long, userId: Long, date: String): List<Attendance>

}