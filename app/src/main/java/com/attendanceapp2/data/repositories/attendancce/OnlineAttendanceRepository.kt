package com.attendanceapp2.data.repositories.attendancce

import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.network.HttpRoutes
import com.professorsattendanceapp.network.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import timber.log.Timber
import java.io.IOException

class OnlineAttendanceRepository(private val ktorClient: HttpClient = KtorClient()) {
    suspend fun getAllAttendances(): List<Attendance> {
        return try {
            val response: List<Attendance> = ktorClient.request(HttpRoutes.ATTENDANCES) {
                method = HttpMethod.Get
                url(HttpRoutes.ATTENDANCES)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get all attendances")
            emptyList()
        }
    }

    suspend fun addAttendance(attendance: Attendance): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.ADD_ATTENDANCE) {
                method = HttpMethod.Post
                url(HttpRoutes.ADD_ATTENDANCE)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(attendance)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to add attendance")
            "Error adding attendance"
        }
    }

    suspend fun updateAttendance(attendance: Attendance): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.UPDATE_ATTENDANCE) {
                method = HttpMethod.Put
                url(HttpRoutes.UPDATE_ATTENDANCE)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(attendance)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to update attendance")
            "Error updating attendance"
        }
    }

    suspend fun deleteAttendance(attendanceId: Int): String {
        return try {
            val response: String = ktorClient.request("${HttpRoutes.DELETE_ATTENDANCE}/$attendanceId") {
                method = HttpMethod.Delete
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete attendance")
            "Error deleting attendance"
        }
    }

    suspend fun getAttendanceByUserIdSubjectIdDate(userId: Int, subjectId: Int, date: String): List<Attendance> {
        return try {
            val response: List<Attendance> = ktorClient.request("${HttpRoutes.ATTENDANCE_BY_USER_ID_SUBJECT_ID_DATE}/$userId/$subjectId/$date") {
                method = HttpMethod.Get
                url("${HttpRoutes.ATTENDANCE_BY_USER_ID_SUBJECT_ID_DATE}/$userId/$subjectId/$date")
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get attendance by user ID, subject ID, and date")
            emptyList()
        }
    }
}