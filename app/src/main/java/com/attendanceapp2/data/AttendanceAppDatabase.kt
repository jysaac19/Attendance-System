package com.attendanceapp2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.attendanceapp2.data.interfaces.AttendanceDao
import com.attendanceapp2.data.interfaces.NotificationDao
import com.attendanceapp2.data.interfaces.ScheduleDao
import com.attendanceapp2.data.interfaces.SubjectDao
import com.attendanceapp2.data.interfaces.UserDao
import com.attendanceapp2.data.interfaces.UserSubjectCrossRefDao
import com.attendanceapp2.data.model.Notifications
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Subject::class, Schedule::class, User::class, Attendance::class, UserSubjectCrossRef::class, Notifications::class], version = 7, exportSchema = false)
abstract class AttendanceAppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun userDao(): UserDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun userSubjectCrossRefDao(): UserSubjectCrossRefDao
    abstract fun notifDao(): NotificationDao


    companion object {
        @Volatile
        private var Instance: AttendanceAppDatabase? = null

        fun getDatabase(context: Context): AttendanceAppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AttendanceAppDatabase::class.java, "nbs_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}