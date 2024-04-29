package com.attendanceapp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val firstname : String,
    val lastname : String,
    val email : String, //should be @student.nbscollege.edu.ph
    val password : String,
    val usertype : String, //ADMIN, STUDENT, FACULTY
    val department : String //BSCS, BSA, BSE, BSAIS, BSTM
)