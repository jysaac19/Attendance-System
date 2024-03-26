package com.attendanceapp2.data.repositories.student

import com.attendanceapp2.data.model.Student

interface StudentRepository {

    suspend fun insertStudent(student : Student)

    suspend fun updateStudent(student : Student)

    suspend fun deleteStudent(student : Student)

}