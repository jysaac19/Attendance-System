package attendanceappusers.studentapp.subjects.joinsubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.launch

class JoinSubjectViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            updateOfflineSubjects()
        }
    }
    private suspend fun updateOfflineSubjects() {
        offlineSubjectRepository.deleteAllSubjects()
        val subjects = onlineSubjectRepository.getAllSubjects()
        subjects.forEach {
            offlineSubjectRepository.insertSubject(it)
        }
    }

    suspend fun joinSubjectByCode(userId: Int, joinCode: String): Results.JoinSubjectResult {
        updateOfflineSubjects()

        val subject = onlineSubjectRepository.getSubjectByJoinCode(joinCode)

        return if (subject != null) {
            val existingCrossRef = onlineUserSubjectCrossRefRepository.getUserSubCrossRefByUserIdSubjectId(
                subject.id,
                userId
            )

            if (existingCrossRef.isEmpty()) {
                onlineUserSubjectCrossRefRepository.addUserSubCrossRef(
                    UserSubjectCrossRef(
                        userId,
                        subject.id
                    )
                )
                Results.JoinSubjectResult(successMessage = "Successfully joined the subject.")
            } else {
                Results.JoinSubjectResult(failureMessage = "You have already joined this subject.")
            }
        } else {
            Results.JoinSubjectResult(failureMessage = "No subject found with the provided join code.")
        }
    }
}