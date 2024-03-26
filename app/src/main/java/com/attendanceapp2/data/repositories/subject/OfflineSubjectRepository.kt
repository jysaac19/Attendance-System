package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.interfaces.SubjectDao
import com.attendanceapp2.data.model.Subject

class OfflineSubjectRepository(private val subjectDao: SubjectDao) : SubjectRepository {

    override suspend fun insertSubject(subject : Subject) = subjectDao.insert(subject)

    override suspend fun updateSubject(subject : Subject) = subjectDao.update(subject)

    override suspend fun deleteSubject(subject : Subject) = subjectDao.delete(subject)

}