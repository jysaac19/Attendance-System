package com.attendanceapp2.samplektor.local.repository

import com.attendanceapp2.samplektor.local.model.SampleStudent
import com.attendanceapp2.samplektor.local.repository.dao.SampleKtorInterfaceStudent

class SampleStudentOfflineRepository(private val sampleStudentDao: SampleKtorInterfaceStudent) : SampleStudentRepository {

    override suspend fun insertStudent(sampleStudent: SampleStudent) = sampleStudentDao.insert(sampleStudent)

    override suspend fun updateStudent(sampleStudent: SampleStudent) = sampleStudentDao.update(sampleStudent)

    override suspend fun deleteStudent(sampleStudent: SampleStudent) = sampleStudentDao.delete(sampleStudent)

}