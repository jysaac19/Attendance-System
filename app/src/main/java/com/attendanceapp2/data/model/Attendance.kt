package com.attendanceapp2.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
data class Attendance(

    val userId : Long,
    val firstname : String,
    val lastname : String,
    val subjectId : String,
    val subjectName : String,
    val subjectCode : String,
    val date: String,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
)