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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userSubjectCrossRef: UserSubjectCrossRef)

    @Update
    suspend fun update(userSubjectCrossRef: UserSubjectCrossRef)

    @Delete
    suspend fun delete(userSubjectCrossRef: UserSubjectCrossRef)

    @Query("SELECT subjectId FROM UserSubjectCrossRef WHERE userId = :userId")
    suspend fun getSubjectIdsForUser(userId: Long): List<Long>

    @Query("SELECT subjectId FROM UserSubjectCrossRef")
    suspend fun getAllSubjectIds(): List<Long>
}