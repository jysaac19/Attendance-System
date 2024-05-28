package attendanceappusers.adminapp.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.schedule.OnlineScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.launch

class AdminHomeScreenViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineScheduleRepository: OfflineScheduleRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,

    private val onlineUserRepository: OnlineUserRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository,
    private val onlineScheduleRepository: OnlineScheduleRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository
) : ViewModel() {
    init {
        // Fetch and insert Users
        viewModelScope.launch {
            offlineUserRepository.deleteAllUsers()
            val users = onlineUserRepository.getAllUsers()
            users.forEach { user ->
                offlineUserRepository.insertUser(user)
            }
        }

        // Fetch and insert Subjects
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val subjects = onlineSubjectRepository.getAllSubjects()
            subjects.forEach { subject ->
                offlineSubjectRepository.insertSubject(subject)
            }
        }

        // Fetch and insert Attendances
        viewModelScope.launch {
            offlineAttendanceRepository.deleteAllAttendances()
            val attendances = onlineAttendanceRepository.getAllAttendances()
            attendances.forEach { attendance ->
                offlineAttendanceRepository.insertAttendance(attendance)
            }
        }

        // Fetch and insert Schedules
        viewModelScope.launch {
            offlineScheduleRepository.deleteAllSchedules()
            val schedules = onlineScheduleRepository.getAllSchedules()
            schedules.forEach { schedule ->
                offlineScheduleRepository.insertSchedule(schedule)
            }
        }

        // Fetch and insert UserSubjectCrossRefs
        viewModelScope.launch {
            offlineUserSubjectCrossRefRepository.deleteAllUserSubjectCrossRefs()
            val userSubjectCrossRefs = onlineUserSubjectCrossRefRepository.getAllUserSubCrossRef()
            userSubjectCrossRefs.forEach { userSubjectCrossRef ->
                offlineUserSubjectCrossRefRepository.insert(userSubjectCrossRef)
            }
        }
    }
}