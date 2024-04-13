package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.model.UserSubjectCrossRef

interface UserSubjectCrossRefRepository {
    suspend fun insert(userSubjectCrossRef: UserSubjectCrossRef)
    suspend fun update(userSubjectCrossRef: UserSubjectCrossRef)
    suspend fun delete(userSubjectCrossRef: UserSubjectCrossRef)
    suspend fun getSubjectIdsForUser(userId: Long): List<Long>
}