package com.attendanceapp2.data.repositories.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Notifications
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import timber.log.Timber

class OnlineNotifRepository(private val ktorClient: HttpClient = KtorClient()) : ViewModel(){
    suspend fun getAllNotifications(): List<Notifications> {
        return try {
            val response: List<Notifications> = ktorClient.request(HttpRoutes.NOTIFICATIONS) {
                method = HttpMethod.Get
                url(HttpRoutes.NOTIFICATIONS)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get all notifications")
            throw e
        }
    }

    suspend fun insertNotification(notifications: Notifications): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.ADD_NOTIFICATIONS) {
                method = HttpMethod.Post
                url(HttpRoutes.ADD_NOTIFICATIONS)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(notifications)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to add notifications")
            "Error adding notifications"
        }
    }
}