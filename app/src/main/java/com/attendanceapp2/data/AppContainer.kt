package com.attendanceapp2.data

import android.content.Context
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.schedule.ScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.UserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.posts.repository.OnlinePostRepository

interface AppContainer {
    val onlinePostRepository: OnlinePostRepository
    val subjectRepository: SubjectRepository
    val userRepository: UserRepository
    val attendanceRepository: AttendanceRepository
    val scheduleRepository: ScheduleRepository
    val userSubjectCrossRefRepository: UserSubjectCrossRefRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(private val context: Context, private val embeddedUsers: List<User>) : AppContainer {
    override val onlinePostRepository: OnlinePostRepository by lazy {
        OnlinePostRepository()
    }

    /**
     * Implementation for [subjectRepository]
     */
    override val subjectRepository: SubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao())
    }

    /**
     * Implementation for [scheduleRepository]
     */
    override val scheduleRepository: ScheduleRepository by lazy {
        OfflineScheduleRepository(AttendanceAppDatabase.getDatabase(context).scheduleDao())
    }

    /**
     * Implementation for [userRepository]
     */
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(AttendanceAppDatabase.getDatabase(context).userDao(), embeddedUsers)
    }

    /**
     * Implementation for [attendanceRepository]
     */
    override val attendanceRepository: AttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao())
    }

    /**
     * Implementation for [userSubjectCrossRefRepository]
     */
    override val userSubjectCrossRefRepository: UserSubjectCrossRefRepository by lazy {
        OfflineUserSubjectCrossRefRepository(AttendanceAppDatabase.getDatabase(context).userSubjectCrossRefDao())
    }
}
