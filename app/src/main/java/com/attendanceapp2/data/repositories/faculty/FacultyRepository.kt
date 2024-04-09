package com.attendanceapp2.data.repositories.faculty

import com.attendanceapp2.data.model.Faculty

interface FacultyRepository {

    suspend fun insertFaculty(attendance: Faculty)

    suspend fun updateFaculty(attendance : Faculty)

    suspend fun deleteFaculty(attendance : Faculty)

}