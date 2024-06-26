package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.subject.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Query("DELETE FROM `Subject`")
    suspend fun deleteAllSubject()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("SELECT * FROM Subject WHERE id IN (:subjectIds) AND subjectStatus = :status")
    suspend fun getSubjectsByIdsAndStatus(subjectIds: List<Int>, status: String): List<Subject>

    @Query("SELECT * FROM Subject")
    fun getAllSubjects(): Flow<List<Subject>>

    @Query("SELECT * FROM Subject WHERE code LIKE '%' || :subjectCode || '%'")
    fun filterSubjectList(subjectCode: String): Flow<List<Subject>>

    @Query("SELECT * FROM Subject WHERE joinCode = :joinCode")
    suspend fun getSubjectByJoinCode(joinCode: String): Subject?

    @Query("SELECT * FROM Subject WHERE code = :subjectCode AND subjectStatus = 'Active'")
    suspend fun getActiveSubjectByCode(subjectCode: String): Subject?

    @Query("SELECT * FROM Subject WHERE name = :subjectName AND subjectStatus = 'Active'")
    suspend fun getActiveSubjectByName(subjectName: String): Subject?

    @Query("SELECT * FROM Subject WHERE (id LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%') AND subjectStatus = :status")
    fun searchSubjectByStatus(query: String, status: String): Flow<List<Subject>>

    @Query("SELECT * FROM Subject WHERE (id LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%')")
    fun searchSubject(query: String): Flow<List<Subject>>

    @Query("SELECT * FROM Subject WHERE subjectStatus = :status")
    fun getSubjectsByStatus(status: String): Flow<List<Subject>>
}