package com.attendanceapp2.data.model.subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int = 0,
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String,
    @SerialName("room")
    val room: String,
    @ColumnInfo(name = "faculty_name")
    @SerialName("facultyName")
    val facultyName: String,
    @SerialName("subjectStatus")
    val subjectStatus: String, // Active, Archived
    @SerialName("joinCode")
    val joinCode: String
)