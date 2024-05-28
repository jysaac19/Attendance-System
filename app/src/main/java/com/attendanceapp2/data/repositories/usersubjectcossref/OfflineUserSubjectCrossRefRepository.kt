package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.interfaces.UserSubjectCrossRefDao
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef

class OfflineUserSubjectCrossRefRepository(
    private val userSubjectCrossRefDao: UserSubjectCrossRefDao
) {
    suspend fun deleteAllUserSubjectCrossRefs() {
        userSubjectCrossRefDao.deleteAllUserSubjectCrossRefs()
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

    suspend fun getJoinedSubjectsForUser(userId: Int): List<UserSubjectCrossRef> {
        return userSubjectCrossRefDao.getJoinedSubjectsOfUser(userId)
    }

    suspend fun getAllSubjects(): List<Int> {
        return userSubjectCrossRefDao.getAllSubjectIds()
    }

    suspend fun getUserSubjectCrossRefBySubjectAndUser(subjectId: Int, userId: Int): UserSubjectCrossRef? {
        return userSubjectCrossRefDao.getUserSubjectCrossRefBySubjectAndUser(subjectId, userId)
    }

    suspend fun getUserSubjectCrossRefBySubject(subjectId: Int): List<UserSubjectCrossRef> {
        return userSubjectCrossRefDao.getUserSubjectCrossRefBySubject(subjectId)
    }
}