package com.attendanceapp2.data

import android.content.Context
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.notification.OfflineNotifRepository
import com.attendanceapp2.data.repositories.notification.OnlineNotifRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.schedule.OnlineScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import com.professorsattendanceapp.network.KtorClient

interface AppContainer {
    //USER REPOSITORY
    val offlineUserRepository: OfflineUserRepository
    val onlineUserRepository: OnlineUserRepository

    //ATTENDANCE REPOSITORY
    val onlineAttendanceRepository: OnlineAttendanceRepository
    val offlineAttendanceRepository: OfflineAttendanceRepository

    //SUBJECT REPOSITORY
    val offlineSubjectRepository: OfflineSubjectRepository
    val onlineSubjectRepository: OnlineSubjectRepository

    //SCHEDULE REPOSITORY
    val offlineScheduleRepository: OfflineScheduleRepository
    val onlineScheduleRepository: OnlineScheduleRepository

    //USERSUBJECTCROSSREF REPOSITORY
    val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository
    val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository

    //Notif Repository

    val offlineNotifRepository: OfflineNotifRepository
    val onlineNotifRepository: OnlineNotifRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(
    private val context: Context
) : AppContainer {
    /**
     * Implementation for [offlineSubjectRepository]
     */
    override val offlineSubjectRepository: OfflineSubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao())
    }

    /**
     * Implementation for [onlineAttendanceRepository]
     */
    override val onlineSubjectRepository: OnlineSubjectRepository by lazy {
        OnlineSubjectRepository(KtorClient())
    }

    /**
     * Implementation for [offlineScheduleRepository]
     */
    override val offlineScheduleRepository: OfflineScheduleRepository by lazy {
        OfflineScheduleRepository(AttendanceAppDatabase.getDatabase(context).scheduleDao())
    }

    /**
     * Implementation for [onlineScheduleRepository]
     */
    override val onlineScheduleRepository: OnlineScheduleRepository by lazy {
        OnlineScheduleRepository(KtorClient())
    }

    /**
     * Implementation for [offlineUserRepository]
     */
    override val offlineUserRepository: OfflineUserRepository by lazy {
        OfflineUserRepository(AttendanceAppDatabase.getDatabase(context).userDao())
    }

    /**
     * Implementation for [onlineUserRepository]
     */
    override val onlineUserRepository: OnlineUserRepository by lazy {
        OnlineUserRepository(KtorClient())
    }

    /**
     * Implementation for [offlineUserSubjectCrossRefRepository]
     */
    override val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository by lazy {
        OfflineUserSubjectCrossRefRepository(AttendanceAppDatabase.getDatabase(context).userSubjectCrossRefDao())
    }

    /**
     * Implementation for [onlineUserSubjectCrossRefRepository]
     */
    override val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository by lazy {
        OnlineUserSubjectCrossRefRepository(KtorClient())
    }

    //ATTENDANCE REPOSITORIES

    /**
     * Implementation for [offlineAttendanceRepository]
     */
    override val offlineAttendanceRepository: OfflineAttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao())
    }

    /**
     * Implementation for [onlineAttendanceRepository]
     */
    override val onlineAttendanceRepository: OnlineAttendanceRepository by lazy {
        OnlineAttendanceRepository(KtorClient())
    }

    // Notifications Repositories

    /**
     * Implementation for [offlineNotifRepository]
     */
    override val offlineNotifRepository: OfflineNotifRepository by lazy {
        OfflineNotifRepository(AttendanceAppDatabase.getDatabase(context).notifDao())
    }

    /**
     * Implementation for [onlineNotifRepository]
     */
    override val onlineNotifRepository: OnlineNotifRepository by lazy {
        OnlineNotifRepository(KtorClient())
    }
}