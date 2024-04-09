package com.attendanceapp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val firstname : String,
    val lastname : String,
    val subjectName : String,
    val subjectCode : String,
    val date: String,
    val time: String
)