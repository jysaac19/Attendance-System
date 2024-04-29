package attendanceappusers.adminapp.homescreen.adduser

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository

class AddUserViewModel (
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository
) : ViewModel() {

}