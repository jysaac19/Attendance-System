package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.attendanceapp2.data.model.User
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
}