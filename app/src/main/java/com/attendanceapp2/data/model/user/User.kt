package com.attendanceapp2.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val firstname : String,
    val lastname : String,
    val email : String, //should be @student.nbscollege.edu.ph
    val password : String,
    val usertype : String, //Admin, Student, Faculty
    val department : String, //BSCS, BSA, BSE, BSAIS, BSTM
    val status : String //Active, Inactive
)