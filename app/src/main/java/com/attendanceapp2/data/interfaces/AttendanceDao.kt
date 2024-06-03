package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.attendance.AttendanceCounts
import com.attendanceapp2.data.model.attendance.AttendanceSummary
import com.attendanceapp2.data.model.attendance.UserDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("DELETE FROM `Attendance`")
    suspend fun deleteAllAttendance()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    @Update
    suspend fun update(attendance: Attendance)

    @Delete
    suspend fun delete(attendance: Attendance)

    @Query("SELECT * FROM Attendance")
    fun getAllAttendances(): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId AND date = :date")
    fun getAttendancesByUserIdSubjectIdAndDate(userId: Int, subjectId : Int, date : String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterStudentAttendanceBySubjectCodeAndDateRange(userId: Int, subjectCode: String, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun filterStudentAttendanceByDateRange(userId: Int, startDate: String, endDate: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesBySubjectCodeAndDateRange(startDate: String, endDate: String, subjectCode: String): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId")
    fun getAttendancesByUserId(userId: Int): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectId IN (:subjectId)")
    fun getAttendancesBySubjectId(subjectId: Int): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE userId = :userId AND subjectId = :subjectId")
    fun getAttendancesByUserIdAndSubjectId(userId: Int, subjectId: Int): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE subjectCode IN (:subjectCodes) AND date BETWEEN :startDate AND :endDate")
    fun filterAttendancesBySubjectCodesAndDateRange(subjectCodes: List<String>, startDate: String, endDate: String): Flow<List<Attendance>>

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

    @Query("""
    SELECT COUNT(DISTINCT userId)
    FROM Attendance 
    WHERE subjectCode = :subjectCode 
    GROUP BY userId 
    HAVING SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) = 0
""")
    fun getStudentsWithPerfectAttendanceCount(subjectCode: String): Flow<Int>

    @Query("""
    SELECT COUNT(DISTINCT userId)
    FROM Attendance 
    WHERE subjectCode = :subjectCode 
    GROUP BY userId 
    HAVING SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) >= 3
""")
    fun getStudentsWithThreeOrMoreAbsencesCount(subjectCode: String): Flow<Int>

    @Query("""
    SELECT COUNT(DISTINCT userId)
    FROM Attendance 
    WHERE subjectCode = :subjectCode 
    GROUP BY userId 
    HAVING SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) = 2
""")
    fun getStudentsWithTwoAbsencesCount(subjectCode: String): Flow<Int>

    @Query("""
    SELECT COUNT(DISTINCT userId)
    FROM Attendance 
    WHERE subjectCode = :subjectCode 
    GROUP BY userId 
    HAVING SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) = 1
""")
    fun getStudentsWithOneAbsenceCount(subjectCode: String): Flow<Int>

    @Query("""
        SELECT 
            userId,
            firstname,
            lastname,
            SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS presentCount,
            SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) AS absentCount,
            SUM(CASE WHEN status = 'Late' THEN 1 ELSE 0 END) AS lateCount,
            COUNT(*) AS totalCount
        FROM Attendance 
        WHERE userId = :userId AND subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getStatusCounts(
        userId: Int,
        subjectCode: String,
        startDate: String,
        endDate: String
    ): AttendanceCounts?

    @Query("""
        SELECT * FROM Attendance 
        WHERE userId = :userId AND subjectCode = :subjectCode AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getAttendances(
        userId: Int,
        subjectCode: String,
        startDate: String,
        endDate: String
    ): List<Attendance>

    @Query("""
        SELECT DISTINCT userId, firstname, lastname 
        FROM Attendance 
        WHERE userId = :userId
        LIMIT 1
    """)
    suspend fun getUserDetails(userId: Int): UserDetails?

    suspend fun getAttendanceSummary(
        userId: Int,
        subjectCode: String,
        startDate: String,
        endDate: String
    ): AttendanceSummary {
        val counts = getStatusCounts(userId, subjectCode, startDate, endDate)
        val attendances = getAttendances(userId, subjectCode, startDate, endDate)

        val firstname: String
        val lastname: String

        if (counts == null || counts.totalCount == 0) {
            val userDetails = getUserDetails(userId)
            firstname = userDetails?.firstname ?: ""
            lastname = userDetails?.lastname ?: ""
        } else {
            firstname = counts.firstname ?: ""
            lastname = counts.lastname ?: ""
        }

        return AttendanceSummary(
            userId = userId,
            firstname = firstname,
            lastname = lastname,
            presentCount = counts?.presentCount ?: 0,
            absentCount = counts?.absentCount ?: 0,
            lateCount = counts?.lateCount ?: 0,
            totalCount = counts?.totalCount ?: 0,
            attendances = attendances
        )
    }
}