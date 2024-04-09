package com.attendanceapp2.samplektor.local.repository

import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.samplektor.local.model.SampleFaculty

interface SampleFacultyRepository {

    suspend fun insertFaculty(sampleFaculty: SampleFaculty)

    suspend fun updateFaculty(sampleFaculty: SampleFaculty)

    suspend fun deleteFaculty(sampleFaculty: SampleFaculty)

}