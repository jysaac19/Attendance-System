package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.interfaces.UserSubjectCrossRefDao
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.model.UserSubjectCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfflineUserSubjectCrossRefRepository(private val userSubjectCrossRefDao: UserSubjectCrossRefDao, private val userSubjectCrossRefs: List<UserSubjectCrossRef>) : UserSubjectCrossRefRepository {

    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            userSubjectCrossRefs.forEach { userSubjectCrossRef -> userSubjectCrossRefDao.insert(userSubjectCrossRef) }
        }
    }

    override suspend fun insert(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.insert(userSubjectCrossRef)
    }

    override suspend fun update(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.update(userSubjectCrossRef)
    }

    override suspend fun delete(userSubjectCrossRef: UserSubjectCrossRef) {
        userSubjectCrossRefDao.delete(userSubjectCrossRef)
    }

    override suspend fun getSubjectIdsForUser(userId: Long): List<Long> {
        return userSubjectCrossRefDao.getSubjectIdsForUser(userId)
    }
}