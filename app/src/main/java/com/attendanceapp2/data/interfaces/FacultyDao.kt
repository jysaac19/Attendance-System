package com.attendanceapp2.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.attendanceapp2.data.model.Faculty

/**
 * Database access object to access the NBS database
 */
@Dao
interface FacultyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(faculty: Faculty)

    @Update
    suspend fun update(faculty: Faculty)

    @Delete
    suspend fun delete(faculty: Faculty)

}