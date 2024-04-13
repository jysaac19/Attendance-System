package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.interfaces.UserSubjectCrossRefDao
import com.attendanceapp2.data.model.UserSubjectCrossRef

class OfflineUserSubjectCrossRefRepository(private val userSubjectCrossRefDao: UserSubjectCrossRefDao) : UserSubjectCrossRefRepository {
    override suspend fun insertUserSubjectCrossRef(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.insertUserSubjectCrossRef(userSubjectCrossRef)
    }

    override suspend fun getUserSubjectCrossRef(userId: Long, subjectId: Long): UserSubjectCrossRef? {
        return userSubjectCrossRefDao.getUserSubjectCrossRef(userId, subjectId)
    }

    override suspend fun deleteUserSubjectCrossRef(userId: Long, subjectId: Long) {
        userSubjectCrossRefDao.deleteUserSubjectCrossRef(userId, subjectId)
    }

    override suspend fun getSubjectIdsForUser(userId: Long): List<Long> {
        return userSubjectCrossRefDao.getSubjectIdsForUser(userId)
    }
}