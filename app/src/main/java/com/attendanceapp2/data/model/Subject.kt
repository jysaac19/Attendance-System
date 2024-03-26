package com.attendanceapp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val code : String,
    val name : String,
    val room : String,
    val start : String,
    val end : String,
    val day : String,
    val sy : String,
    val semester : Int
)
