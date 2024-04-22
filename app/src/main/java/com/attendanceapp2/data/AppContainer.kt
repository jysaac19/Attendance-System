package com.attendanceapp2.data

import android.content.Context
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.model.UserSubjectCrossRef
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
    val offlineAttendanceRepository: OfflineAttendanceRepository
    val scheduleRepository: ScheduleRepository
    val userSubjectCrossRefRepository: UserSubjectCrossRefRepository
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
    override val subjectRepository: SubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao(), embeddedSubjects)
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
     * Implementation for [offlineAttendanceRepository]
     */
    override val offlineAttendanceRepository: OfflineAttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao(), embeddedAttendances)
    }

    /**
     * Implementation for [userSubjectCrossRefRepository]
     */
    override val userSubjectCrossRefRepository: UserSubjectCrossRefRepository by lazy {
        OfflineUserSubjectCrossRefRepository(AttendanceAppDatabase.getDatabase(context).userSubjectCrossRefDao(), embeddedUserSubjectCrossRefs)
    }
}
