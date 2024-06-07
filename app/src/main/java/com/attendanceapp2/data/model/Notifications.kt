package com.attendanceapp2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "notifications")
data class Notifications(
    @PrimaryKey(autoGenerate = true)
    @SerialName(value = "notifID")
    val notifID: Long = 0,
    @SerialName(value = "portal")
    val portal: String,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "message")
    val message: String
)

