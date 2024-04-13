package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.model.UserSubjectCrossRef

interface UserSubjectCrossRefRepository {
    suspend fun insertUserSubjectCrossRef(userSubjectCrossRef: UserSubjectCrossRef)
    suspend fun getUserSubjectCrossRef(userId: Long, subjectId: Long): UserSubjectCrossRef?
    suspend fun deleteUserSubjectCrossRef(userId: Long, subjectId: Long)

    suspend fun getSubjectIdsForUser(userId: Long): List<Long>
}