package com.attendanceapp2.data.model.attendance

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId : Int,
    val firstname : String,
    val lastname : String,
    val subjectId : Int,
    val subjectName : String,
    val subjectCode : String,
    val date: String,
    val time: String,
    val status: String, //Present, Absent, Late
    val usertype : String //Student, Teacher
)