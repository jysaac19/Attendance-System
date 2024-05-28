package com.attendanceapp2.data.model.subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Subject Schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val subjectId : Int,
    val subjectCode : String,
    val subjectName : String,
    val day : String,
    @ColumnInfo("start_time")
    val start : String,
    @ColumnInfo("end_time")
    val end : String
)