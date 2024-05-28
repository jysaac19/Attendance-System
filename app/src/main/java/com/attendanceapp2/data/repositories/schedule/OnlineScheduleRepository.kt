package com.attendanceapp2.data.repositories.schedule

import com.attendanceapp2.data.model.subject.Schedule
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

class OnlineScheduleRepository(private val ktorClient: HttpClient = KtorClient()) {
    suspend fun getAllSchedules(): List<Schedule> {
        return try {
            val response: List<Schedule> = ktorClient.request(HttpRoutes.SCHEDULES) {
                method = HttpMethod.Get
                url(HttpRoutes.SCHEDULES)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get all schedules")
            emptyList()
        }
    }

    suspend fun addSchedule(schedule: Schedule): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.ADD_SCHEDULE) {
                method = HttpMethod.Post
                url(HttpRoutes.ADD_SCHEDULE)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(schedule)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to add schedule")
            "Error adding schedule"
        }
    }

    suspend fun updateSchedule(schedule: Schedule): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.UPDATE_SCHEDULE) {
                method = HttpMethod.Put
                url(HttpRoutes.UPDATE_SCHEDULE)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(schedule)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to update schedule")
            "Error updating schedule"
        }
    }

    suspend fun deleteSchedule(scheduleId: Int): String {
        return try {
            val response: String = ktorClient.request("${HttpRoutes.DELETE_SCHEDULE}/$scheduleId") {
                method = HttpMethod.Delete
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete schedule")
            "Error deleting schedule"
        }
    }

    suspend fun scheduleBySubjectId(subjectId: Int): List<Schedule> {
        return try {
            val response: List<Schedule> = ktorClient.request("${HttpRoutes.SCHEDULE_BY_SUBJECT_ID}/$subjectId") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get schedule by subject ID")
            emptyList()
        }
    }
}