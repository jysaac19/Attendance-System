package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.model.attendance.Attendance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineAttendanceRepository(
    private val attendanceDao: AttendanceDao,
    private val attendances: List<Attendance>
) {
    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            attendances.forEach { attendance -> attendanceDao.insert(attendance) }
        }
    }
    suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)

    suspend fun updateAttendance(attendance: Attendance) = attendanceDao.update(attendance)

    suspend fun deleteAttendance(attendance: Attendance) = attendanceDao.delete(attendance)

    fun getAttendancesByUserIdSubjectIdAndDate(userId: Long, subjectId: Long, date: String): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserIdSubjectIdAndDate(userId, subjectId, date)
    }

    fun filterStudentAttendanceBySubjectCodeAndDateRange(startDate: String, endDate: String, userId: Long, subjectCode: String): Flow<List<Attendance>> {
        return attendanceDao.filterStudentAttendanceBySubjectCodeAndDateRange(startDate, endDate, userId, subjectCode)
    }

    fun filterStudentAttendanceByDateRange(startDate: String, endDate: String, userId: Long): Flow<List<Attendance>>{
        return attendanceDao.filterStudentAttendanceByDateRange(startDate, endDate, userId)
    }
    fun filterAttendancesBySubjectCodeAndDateRange(startDate: String, endDate: String, subjectCode: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesBySubjectCodeAndDateRange(startDate, endDate, subjectCode)
    }

    fun getAttendancesByUserId(userId: Long): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserId(userId)
    }

    fun getAllAttendances() : Flow<List<Attendance>> {
        return attendanceDao.getAllAttendances()
    }

    fun filterAttendancesBySubjectIdsAndDateRange(subjectCodes : List<String>, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesBySubjectCodesAndDateRange(subjectCodes, startDate, endDate)
    }

    fun filterAttendancesByQueryAndDateRange(query: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesByQueryAndDateRange(
            query = query,
            startDate = startDate,
            endDate = endDate
        )
    }

    fun filterAttendancesByQuerySubjectCodeAndDateRange(query: String, subjectCode: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesByQuerySubjectCodeAndDateRange(
            query = query,
            subjectCode = subjectCode,
            startDate = startDate,
            endDate = endDate
        )
    }

    fun filterAttendanceByDateRange(startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendanceByDateRange(
            startDate = startDate,
            endDate = endDate
        )
    }

    fun filterAttendancesBySubjectCodeUserTypeAndDateRange(subjectCode: String, userType: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesBySubjectCodeUserTypeAndDateRange(
            subjectCode = subjectCode,
            userType = userType,
            startDate = startDate,
            endDate = endDate
        )
    }

    fun filterAttendancesByUserTypeAndDateRange (userType: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesByUserTypeAndDateRange(
            userType = userType,
            startDate = startDate,
            endDate = endDate
        )
    }

    fun filterAttendancesByQuerySubjectCodeUserTypeAndDateRange (query: String, subjectCode: String, userType: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesByQuerySubjectCodeUserTypeAndDateRange(
            query = query,
            subjectCode = subjectCode,
            userType = userType,
            startDate = startDate,
            endDate = endDate
        )
    }

    fun filterAttendancesByQueryUserTypeAndDateRange (query: String, userType: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesByQueryUserTypeAndDateRange(
            query = query,
            userType = userType,
            startDate = startDate,
            endDate = endDate
        )
    }
}