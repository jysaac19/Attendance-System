package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    @Update
    suspend fun update(attendance: Attendance)

    @Delete
    suspend fun delete(attendance: Attendance)

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId AND date = :date")
    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId : Long, date : String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectCode = :subjectCode AND userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun filterAttendance(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId")
    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId IN (:userIds)")
    fun getAttendancesByUserIds(userIds: List<Long>): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectId IN (:subjectIds)")
    fun getAttendancesBySubjectIds(subjectIds: List<Long>): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId")
    fun getAttendancesByUserIdAndSubjectId(userId: Long, subjectId: Long): Flow<List<Attendance>>


}


