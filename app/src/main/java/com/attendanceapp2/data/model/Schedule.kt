package com.attendanceapp2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Subject Schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val subjectId : Long,
    val subjectCode : String,
    val subjectName : String,
    val day : String,
    @ColumnInfo("start_time")
    val start : String,
    @ColumnInfo("end_time")
    val end : String
)