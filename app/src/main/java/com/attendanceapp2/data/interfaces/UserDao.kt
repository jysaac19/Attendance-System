package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("DELETE FROM `User`")
    suspend fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM User WHERE usertype = :userType")
    fun getUsersByUserType(userType: String): Flow<List<User>>

    @Query("SELECT * FROM User")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE usertype = 'Student'")
    fun getStudents(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE id IN (:userIds) AND usertype = 'Student'")
    suspend fun getUsersByIds(userIds: List<Int>): List<User>

    @Query("SELECT * FROM User WHERE (firstname LIKE '%' || :query || '%' OR lastname LIKE '%' || :query || '%' OR id LIKE '%' || :query || '%') AND usertype = :usertype")
    fun filterUsersByQueryAndUserType(query: String, usertype: String): Flow<List<User>>

    @Query("SELECT * FROM User WHERE usertype = :usertype")
    fun filterUsersByUserType(usertype: String): Flow<List<User>>
    @Query("SELECT * FROM User WHERE firstname LIKE '%' || :query || '%' OR lastname LIKE '%' || :query || '%' OR id LIKE '%' || :query || '%'")
    fun filterUsersByQuery(query: String): Flow<List<User>>

    @Query("SELECT * FROM User WHERE firstname = :firstname AND lastname = :lastname")
    suspend fun getUserByFullName(firstname: String, lastname: String): User?

    @Query("SELECT * FROM User WHERE (firstname LIKE '%' || :query || '%' OR lastname LIKE '%' || :query || '%' OR id LIKE '%' || :query || '%') AND usertype = 'Student'")
    fun searchUser(query: String): Flow<List<User>>
}