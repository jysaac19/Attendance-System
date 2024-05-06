package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.user.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

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
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE id LIKE :userId || '%' AND usertype = :usertype")
    fun filterUsersByAdmin(
        userId: String,
        usertype: String
    ): Flow<List<User>>

    @Query("SELECT * FROM User WHERE id LIKE :userIdPrefix || '%'")
    fun filterUsersByStartingUserId(userIdPrefix: String): Flow<List<User>>

    @Query("SELECT * FROM User WHERE firstname = :firstname AND lastname = :lastname")
    suspend fun getUserByFullName(firstname: String, lastname: String): User?
}