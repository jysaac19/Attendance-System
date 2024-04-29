package com.attendanceapp2.data

import android.content.Context
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.model.UserSubjectCrossRef
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.posts.repository.OnlinePostRepository

interface AppContainer {
    val onlinePostRepository: OnlinePostRepository
    val offlineSubjectRepository: OfflineSubjectRepository
    val offlineUserRepository: OfflineUserRepository
    val offlineAttendanceRepository: OfflineAttendanceRepository
    val offlineScheduleRepository: OfflineScheduleRepository
    val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(
    private val context: Context,
    private val embeddedUsers: List<User>,
    private val embeddedSubjects: List<Subject>,
    private val embeddedAttendances : List<Attendance>,
    private val embeddedUserSubjectCrossRefs: List<UserSubjectCrossRef>
) : AppContainer {
    override val onlinePostRepository: OnlinePostRepository by lazy {
        OnlinePostRepository()
    }

    /**
     * Implementation for [subjectRepository]
     */
    override val offlineSubjectRepository: OfflineSubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao(), embeddedSubjects)
    }

    /**
     * Implementation for [scheduleRepository]
     */
    override val offlineScheduleRepository: OfflineScheduleRepository by lazy {
        OfflineScheduleRepository(AttendanceAppDatabase.getDatabase(context).scheduleDao())
    }

    /**
     * Implementation for [userRepository]
     */
    override val offlineUserRepository: OfflineUserRepository by lazy {
        OfflineUserRepository(AttendanceAppDatabase.getDatabase(context).userDao(), embeddedUsers)
    }

    /**
     * Implementation for [offlineAttendanceRepository]
     */
    override val offlineAttendanceRepository: OfflineAttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao(), embeddedAttendances)
    }

    /**
     * Implementation for [userSubjectCrossRefRepository]
     */
    override val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository by lazy {
        OfflineUserSubjectCrossRefRepository(AttendanceAppDatabase.getDatabase(context).userSubjectCrossRefDao(), embeddedUserSubjectCrossRefs)
    }
}
