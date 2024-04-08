package com.attendanceapp2.data

import android.content.Context
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.UserRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository

interface AppContainer {
    val subjectRepository: SubjectRepository
    val userRepository: UserRepository
    val attendanceRepository: AttendanceRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [subjectRepository]
     */
    override val subjectRepository: SubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao())
    }
    /**
     * Implementation for [userRepository]
     */
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(AttendanceAppDatabase.getDatabase(context).userDao())
    }
    /**
     * Implementation for [attendanceRepository]
     */
    override val attendanceRepository: AttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao())
    }
}
