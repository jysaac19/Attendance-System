package attendanceappusers.studentapp.screens.subjects.joinsubject

import android.util.Log
import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.UserSubjectCrossRef
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository

class JoinSubjectViewModel (
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    suspend fun joinSubjectByCode(userId: Long, joinCode: String): Results.JoinSubjectResult {
        // Retrieve subject with the provided join code
        val subject = offlineSubjectRepository.getSubjectByJoinCode(joinCode)
        return if (subject != null) {
            // If subject found, create user-subject cross-reference
            offlineUserSubjectCrossRefRepository.insert(UserSubjectCrossRef(userId, subject.id))
            Results.JoinSubjectResult(successMessage = "Successfully joined the subject.")
        } else {
            // Handle case where no subject is found with the provided join code
            Results.JoinSubjectResult(failureMessage = "No subject found with the provided join code.")
        }
    }
}
