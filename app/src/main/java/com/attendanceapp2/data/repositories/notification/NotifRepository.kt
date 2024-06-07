package com.attendanceapp2.data.repositories.notification

import com.attendanceapp2.data.model.Notifications
import kotlinx.coroutines.flow.Flow


interface NotifRepository {

    suspend fun insertNotifications(notification: Notifications)
    fun getAllNotifications(): Flow<List<Notifications>>
}