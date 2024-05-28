package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.model.attendance.Attendance

import kotlinx.coroutines.flow.Flow

class OfflineAttendanceRepository(
    private val attendanceDao: AttendanceDao
) {
    suspend fun deleteAllAttendances() {
        attendanceDao.deleteAllAttendance()
    }

    suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)

    suspend fun updateAttendance(attendance: Attendance) = attendanceDao.update(attendance)

    suspend fun deleteAttendance(attendance: Attendance) = attendanceDao.delete(attendance)

    fun getAttendancesByUserIdSubjectIdAndDate(userId: Int, subjectId: Int, date: String): Flow<List<Attendance>> {
        return attendanceDao.getAttendancesByUserIdSubjectIdAndDate(userId, subjectId, date)
    }

    fun filterStudentAttendanceBySubjectCodeAndDateRange(userId: Int, subjectCode: String, startDate: String, endDate: String): Flow<List<Attendance>> {
        return attendanceDao.filterStudentAttendanceBySubjectCodeAndDateRange(userId, subjectCode, startDate, endDate)
    }

    fun filterStudentAttendanceByDateRange(userId: Int, startDate: String, endDate: String): Flow<List<Attendance>>{
        return attendanceDao.filterStudentAttendanceByDateRange(userId, startDate, endDate)
    }
    fun filterAttendancesBySubjectCodeAndDateRange(startDate: String, endDate: String, subjectCode: String): Flow<List<Attendance>> {
        return attendanceDao.filterAttendancesBySubjectCodeAndDateRange(startDate, endDate, subjectCode)
    }

    fun getAttendancesByUserId(userId: Int): Flow<List<Attendance>> {
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