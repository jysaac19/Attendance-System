package attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UpdateSubjectViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {
    val facultyList: Flow<List<User>> = offlineUserRepository.getUsersByUserType("Faculty")

    fun updateSubject(updatedSubject: Subject) {
        viewModelScope.launch {
            offlineSubjectRepository.updateSubject(updatedSubject)
        }
    }

    fun validateFields(subject: Subject): Results.UpdateSubjectResult {
        return when {
            subject.code.isBlank() || subject.name.isBlank() || subject.room.isBlank() ||
            subject.faculty.isBlank() || subject.subjectStatus.isBlank() || subject.joinCode.isBlank()
            -> Results.UpdateSubjectResult(failureMessage = "All fields are required.")

            else -> Results.UpdateSubjectResult(successMessage = "Fields are valid.")
        }
    }
}