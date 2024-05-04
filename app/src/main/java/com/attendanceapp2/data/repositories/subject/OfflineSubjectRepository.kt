package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.interfaces.SubjectDao
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineSubjectRepository(
    private val subjectDao: SubjectDao,
    private val subjects: List<Subject>
) {

    init {
        // Initialize the database with the list of subjects
        CoroutineScope(Dispatchers.IO).launch {
            subjects.forEach { subject -> subjectDao.insert(subject) }
        }
    }

    suspend fun insertSubject(subject : Subject) = subjectDao.insert(subject)

    suspend fun updateSubject(subject : Subject) = subjectDao.update(subject)

    suspend fun deleteSubject(subject : Subject) = subjectDao.delete(subject)

    suspend fun getSubjectsByIds(subjectIds: List<Long>): List<Subject> {
        return withContext(Dispatchers.IO) {
            subjectDao.getSubjectsByIds(subjectIds)
        }
    }

    suspend fun getAllSubjects(): List<Subject> {
        return withContext(Dispatchers.IO) {
            subjectDao.getAllSubjects()
        }
    }

    fun filterSubjectList(subjectCode: String): Flow<List<Subject>> {
        return subjectDao.filterSubjectList(subjectCode)
    }
}