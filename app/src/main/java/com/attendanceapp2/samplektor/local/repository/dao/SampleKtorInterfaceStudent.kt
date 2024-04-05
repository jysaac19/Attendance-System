package com.attendanceapp2.samplektor.local.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.samplektor.local.model.SampleFaculty
import com.attendanceapp2.samplektor.local.model.SampleStudent

@Dao
interface SampleKtorInterfaceStudent {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sampleStudent: SampleStudent)

    @Update
    suspend fun update(sampleStudent: SampleStudent)

    @Delete
    suspend fun delete(sampleStudent: SampleStudent)

}