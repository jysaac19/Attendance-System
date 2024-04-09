package com.attendanceapp2.data.repositories.faculty

import com.attendanceapp2.data.interfaces.FacultyDao
import com.attendanceapp2.data.model.Faculty

class OfflineFacultyRepository(private val facultyDao: FacultyDao) : FacultyRepository {

    override suspend fun insertFaculty(faculty: Faculty) = facultyDao.insert(faculty)

    override suspend fun updateFaculty(faculty: Faculty) = facultyDao.update(faculty)

    override suspend fun deleteFaculty(faculty: Faculty) = facultyDao.delete(faculty)

}