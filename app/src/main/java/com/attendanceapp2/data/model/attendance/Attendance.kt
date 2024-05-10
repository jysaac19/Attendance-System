package com.attendanceapp2.data.model.attendance

import androidx.room.Entity
import androidx.room.PrimaryKey

// Data class representing attendance information
@Entity(tableName = "Attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val userId : Long,
    val firstname : String,
    val lastname : String,
    val subjectId : Long,
    val subjectName : String,
    val subjectCode : String,
    val date: String,
    val time: String,
    val status: String, //Present, Absent, Late
    val usertype : String //Student, Teacher
)