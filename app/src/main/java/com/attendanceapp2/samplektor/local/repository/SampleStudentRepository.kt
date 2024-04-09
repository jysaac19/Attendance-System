package com.attendanceapp2.samplektor.local.repository

import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.samplektor.local.model.SampleFaculty
import com.attendanceapp2.samplektor.local.model.SampleStudent

interface SampleStudentRepository {

    suspend fun insertStudent(sampleStudent: SampleStudent)

    suspend fun updateStudent(sampleStudent: SampleStudent)

    suspend fun deleteStudent(sampleStudent: SampleStudent)

}