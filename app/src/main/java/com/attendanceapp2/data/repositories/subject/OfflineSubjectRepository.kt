package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.interfaces.SubjectDao
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfflineSubjectRepository(private val subjectDao: SubjectDao, private val subjects: List<Subject>) : SubjectRepository {

    init {
        // Initialize the database with the list of subjects
        CoroutineScope(Dispatchers.IO).launch {
            subjects.forEach { subject -> subjectDao.insert(subject) }
        }
    }

    override suspend fun insertSubject(subject : Subject) = subjectDao.insert(subject)

    override suspend fun updateSubject(subject : Subject) = subjectDao.update(subject)

    override suspend fun deleteSubject(subject : Subject) = subjectDao.delete(subject)

}