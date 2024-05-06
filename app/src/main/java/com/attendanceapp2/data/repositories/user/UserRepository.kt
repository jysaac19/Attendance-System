package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.model.user.User

interface UserRepository {

    suspend fun insertStudent(user : User)

    suspend fun updateStudent(user : User)

    suspend fun deleteStudent(user : User)

    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    suspend fun getUserByEmail(email: String): User?
}