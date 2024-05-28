package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef

@Dao
interface UserSubjectCrossRefDao {
    @Query("DELETE FROM `UserSubjectCrossRef`")
    suspend fun deleteAllUserSubjectCrossRefs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userSubjectCrossRef: UserSubjectCrossRef)

    @Update
    suspend fun update(userSubjectCrossRef: UserSubjectCrossRef)

    @Delete
    suspend fun delete(userSubjectCrossRef: UserSubjectCrossRef)

    @Query("SELECT * FROM UserSubjectCrossRef WHERE userId = :userId")
    suspend fun getJoinedSubjectsOfUser(userId: Int): List<UserSubjectCrossRef>

    @Query("SELECT subjectId FROM UserSubjectCrossRef")
    suspend fun getAllSubjectIds(): List<Int>

    @Query("SELECT * FROM UserSubjectCrossRef WHERE subjectId = :subjectId AND userId = :userId")
    suspend fun getUserSubjectCrossRefBySubjectAndUser(subjectId: Int, userId: Int): UserSubjectCrossRef?

    @Query("SELECT * FROM UserSubjectCrossRef WHERE subjectId = :subjectId")
    suspend fun getUserSubjectCrossRefBySubject(subjectId: Int): List<UserSubjectCrossRef>
}