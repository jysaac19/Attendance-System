package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.attendanceapp2.data.model.UserSubjectCrossRef

@Dao
interface UserSubjectCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserSubjectCrossRef(userSubjectCrossRef: UserSubjectCrossRef)

    @Query("SELECT * FROM UserSubjectCrossRef WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun getUserSubjectCrossRef(userId: Long, subjectId: Long): UserSubjectCrossRef?

    @Query("DELETE FROM UserSubjectCrossRef WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun deleteUserSubjectCrossRef(userId: Long, subjectId: Long)

    @Query("SELECT subjectId FROM UserSubjectCrossRef WHERE userId = :userId")
    suspend fun getSubjectIdsForUser(userId: Long): List<Long>
}