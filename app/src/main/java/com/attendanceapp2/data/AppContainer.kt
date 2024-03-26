package com.attendanceapp2.data

import android.content.Context
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.faculty.FacultyRepository
import com.attendanceapp2.data.repositories.faculty.OfflineFacultyRepository
import com.attendanceapp2.data.repositories.student.OfflineStudentRepository
import com.attendanceapp2.data.repositories.student.StudentRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository

interface AppContainer {
    val facultyRepository: FacultyRepository
    val subjectRepository: SubjectRepository
    val studentRepository: StudentRepository
    val attendanceRepository: AttendanceRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [userRepository]
     */
    override val facultyRepository: FacultyRepository by lazy {
        OfflineFacultyRepository(AttendanceAppDatabase.getDatabase(context).facultyDao())
    }
    /**
     * Implementation for [subjectRepository]
     */
    override val subjectRepository: SubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao())
    }
    /**
     * Implementation for [studentRepository]
     */
    override val studentRepository: StudentRepository by lazy {
        OfflineStudentRepository(AttendanceAppDatabase.getDatabase(context).studentDao())
    }
    /**
     * Implementation for [attendanceRepository]
     */
    override val attendanceRepository: AttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao())
    }
}
