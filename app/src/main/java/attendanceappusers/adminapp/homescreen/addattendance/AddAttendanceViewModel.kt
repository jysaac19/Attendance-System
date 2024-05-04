package attendanceappusers.adminapp.homescreen.addattendance

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository

class AddAttendanceViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository
): ViewModel() {



}