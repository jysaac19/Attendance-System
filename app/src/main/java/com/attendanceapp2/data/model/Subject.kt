package com.attendanceapp2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val code : String,
    val name : String,
    val room : String,
    @ColumnInfo("faculty_name")
    val faculty : String,
    val subjectStatus: String, //Active, Archived
    val joinCode: String
)