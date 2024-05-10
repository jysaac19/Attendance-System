package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.attendance.Attendance
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
    fun getAllAttendances(): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId AND date = :date")
    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId : Long, date : String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterStudentAttendanceBySubjectCodeAndDateRange(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun filterStudentAttendanceByDateRange(startDate: String, endDate: String, userId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesBySubjectCodeAndDateRange(startDate: String, endDate: String, subjectCode: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId")
    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectId IN (:subjectId)")
    fun getAttendancesBySubjectId(subjectId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId")
    fun getAttendancesByUserIdAndSubjectId(userId: Long, subjectId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectId IN (:subjectIds) AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesBySubjectIdsAndDateRange(subjectIds: List<Long>, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE (userId LIKE '%' || :query || '%' OR firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%') AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesByQueryAndDateRange(query: String, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE (userId LIKE '%' || :query || '%' OR firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%') AND subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesByQuerySubjectCodeAndDateRange(query: String, subjectCode: String, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE date BETWEEN :startDate AND :endDate")
    fun filterAttendanceByDateRange(startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectCode = :subjectCode AND usertype = :userType AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesBySubjectCodeUserTypeAndDateRange (subjectCode: String, userType: String, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE usertype = :userType AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesByUserTypeAndDateRange (userType: String, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE (userId LIKE '%' || :query || '%' OR firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%') AND subjectCode = :subjectCode AND usertype = :userType AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesByQuerySubjectCodeUserTypeAndDateRange (query: String, subjectCode: String, userType: String, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE (userId LIKE '%' || :query || '%' OR firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%') AND usertype = :userType AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesByQueryUserTypeAndDateRange (query: String, userType: String, startDate: String, endDate: String): Flow<List<Attendance>>
}