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

    @Query("SELECT * FROM Attendance")
    fun getAttendances(): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId AND date = :date")
    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId : Long, date : String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterStudentAttendance(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterFacultyAttendance(startDate: String, endDate: String, subjectCode: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId")
    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectId IN (:subjectId)")
    fun getAttendancesBySubjectId(subjectId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId")
    fun getAttendancesByUserIdAndSubjectId(userId: Long, subjectId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectId IN (:subjectIds)")
    fun getAttendancesBySubjectIds(subjectIds: List<Long>): Flow<List<Attendance>>

    // Inside AttendanceDao interface

    @Query("SELECT * FROM Attendance WHERE userId LIKE '%' || :userId || '%' AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesByAdmin(
        userId: String,
        startDate: String,
        endDate: String
    ): Flow<List<Attendance>>
}