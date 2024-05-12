package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.interfaces.UserSubjectCrossRefDao
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfflineUserSubjectCrossRefRepository(
    private val userSubjectCrossRefDao: UserSubjectCrossRefDao,
    private val userSubjectCrossRefs: List<UserSubjectCrossRef>
) {

    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            userSubjectCrossRefs.forEach { userSubjectCrossRef -> userSubjectCrossRefDao.insert(userSubjectCrossRef) }
        }
    }

    suspend fun insert(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.insert(userSubjectCrossRef)
    }

    suspend fun update(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.update(userSubjectCrossRef)
    }

    suspend fun delete(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.delete(userSubjectCrossRef)
    }

    suspend fun getJoinedSubjectsForUser(userId: Long): List<UserSubjectCrossRef> {
        return userSubjectCrossRefDao.getJoinedSubjectsForUser(userId)
    }

    suspend fun getAllSubjects(): List<Long> {
        return userSubjectCrossRefDao.getAllSubjectIds()
    }

    suspend fun getUserSubjectCrossRefBySubjectAndUser(subjectId: Long, userId: Long): UserSubjectCrossRef? {
        return userSubjectCrossRefDao.getUserSubjectCrossRefBySubjectAndUser(subjectId, userId)
    }
}