package com.attendanceapp2.data.repositories.student

import com.attendanceapp2.data.interfaces.StudentDao
import com.attendanceapp2.data.model.Student

class OfflineStudentRepository(private val studentDao: StudentDao) : StudentRepository {

    override suspend fun insertStudent(student: Student) = studentDao.insert(student)

    override suspend fun updateStudent(student: Student) = studentDao.update(student)

    override suspend fun deleteStudent(student: Student) = studentDao.delete(student)

}