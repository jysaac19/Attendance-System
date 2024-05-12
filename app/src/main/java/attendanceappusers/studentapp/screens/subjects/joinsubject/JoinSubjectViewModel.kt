package attendanceappusers.studentapp.screens.subjects.joinsubject

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository

class JoinSubjectViewModel (
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    suspend fun joinSubjectByCode(userId: Long, joinCode: String): Results.JoinSubjectResult {
        val subject = offlineSubjectRepository.getSubjectByJoinCode(joinCode)
        return if (subject != null) {
            offlineUserSubjectCrossRefRepository.insert(UserSubjectCrossRef(userId, subject.id))
            Results.JoinSubjectResult(successMessage = "Successfully joined the subject.")
        } else {
            Results.JoinSubjectResult(failureMessage = "No subject found with the provided join code.")
        }
    }
}
