package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.interfaces.SubjectDao
import com.attendanceapp2.data.model.subject.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OfflineSubjectRepository(
    private val subjectDao: SubjectDao
) {
    suspend fun deleteAllSubjects() {
        subjectDao.deleteAllSubject()
    }
    suspend fun insertSubject(subject : Subject) = subjectDao.insert(subject)

    suspend fun updateSubject(subject : Subject) = subjectDao.update(subject)

    suspend fun deleteSubject(subject : Subject) = subjectDao.delete(subject)

    suspend fun getActiveSubjectsByIds(subjectIds: List<Int>): List<Subject> {
        return subjectDao.getSubjectsByIdsAndStatus(subjectIds, "Active")
    }

    suspend fun getArchivedSubjectsByIds(subjectIds: List<Int>): List<Subject> {
        return subjectDao.getSubjectsByIdsAndStatus(subjectIds, "Archived")
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

    fun searchActiveSubject(query: String): Flow<List<Subject>> {
        return subjectDao.searchSubjectByStatus(query, "Active")
    }
    fun searchArchivedSubject(query: String): Flow<List<Subject>> {
        return subjectDao.searchSubjectByStatus(query, "Archived")
    }

    fun getActiveSubjects(): Flow<List<Subject>> {
        return subjectDao.getSubjectsByStatus("Active")
    }

    fun getArchivedSubjects(): Flow<List<Subject>> {
        return subjectDao.getSubjectsByStatus("Archived")
    }
}