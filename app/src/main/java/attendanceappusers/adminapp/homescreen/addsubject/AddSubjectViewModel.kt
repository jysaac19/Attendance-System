package attendanceappusers.adminapp.homescreen.addsubject

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow

class AddSubjectViewModel(
    private val userRepository: OfflineUserRepository,
    private val userSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val subjectRepository: OfflineSubjectRepository,
): ViewModel() {
    val facultyList: Flow<List<User>> = userRepository.getUsersByUserType("Faculty")


}