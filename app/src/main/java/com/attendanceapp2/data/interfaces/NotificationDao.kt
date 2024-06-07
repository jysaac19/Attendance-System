package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.attendanceapp2.data.model.Notifications
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Upsert
    suspend fun insertNotification(notification: Notifications)

    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): Flow<List<Notifications>>
}