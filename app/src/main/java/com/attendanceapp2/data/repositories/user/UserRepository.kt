package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertStudent(user : User)

    suspend fun updateStudent(user : User)

    suspend fun deleteStudent(user : User)

//    fun getUser(user : User) : Flow<User>
}