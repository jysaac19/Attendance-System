package com.attendanceapp2.data.repositories.student

import com.attendanceapp2.data.model.User

interface UserRepository {

    suspend fun insertStudent(user : User)

    suspend fun updateStudent(user : User)

    suspend fun deleteStudent(user : User)

}