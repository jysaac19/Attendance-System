package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.interfaces.SubjectDao
import com.attendanceapp2.data.model.subject.Subject
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

    fun getAllSubjects(): Flow<List<Subject>> {
        return subjectDao.getAllSubjects()
    }

    fun filterSubjectList(subjectCode: String): Flow<List<Subject>> {
        return subjectDao.filterSubjectList(subjectCode)
    }

    suspend fun getSubjectByJoinCode(joinCode: String): Subject? {
        return withContext(Dispatchers.IO) {
            subjectDao.getSubjectByJoinCode(joinCode)
        }
    }

    suspend fun getActiveSubjectByCode(subjectCode: String): Subject? {
        return subjectDao.getActiveSubjectByCode(subjectCode)
    }

    suspend fun getActiveSubjectByName(subjectName: String): Subject? {
        return subjectDao.getActiveSubjectByName(subjectName)
    }

    fun searchSubject(query: String): Flow<List<Subject>> {
        return subjectDao.searchSubject(query)
    }
}