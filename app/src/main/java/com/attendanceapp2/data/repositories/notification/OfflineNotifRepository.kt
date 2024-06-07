package com.attendanceapp2.data.repositories.notification

import com.attendanceapp2.data.interfaces.NotificationDao
import com.attendanceapp2.data.model.Notifications
import kotlinx.coroutines.flow.Flow

class OfflineNotifRepository(
    private val notificationDao: NotificationDao
) : NotifRepository {
    override suspend fun insertNotifications(notification: Notifications) = notificationDao.insertNotification(notification)
    override fun getAllNotifications(): Flow<List<Notifications>> {
        return notificationDao.getAllNotifications()
    }
}