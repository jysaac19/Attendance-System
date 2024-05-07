package attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UpdateSubjectViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository
) : ViewModel() {
    val facultyList: Flow<List<User>> = offlineUserRepository.getUsersByUserType("Faculty")

    private suspend fun getFacultyUserId(fullName: String): Long? {
        val names = fullName.split(" ")
        val firstName = names.firstOrNull() ?: ""
        val lastName = names.drop(1).joinToString(" ")
        val faculty = offlineUserRepository.getUserByFullName(firstName, lastName)
        return faculty?.id
    }

    suspend fun updateSubject(updatedSubject: Subject, oldFaculty: String) {
        viewModelScope.launch {
            // Update the subject
            offlineSubjectRepository.updateSubject(updatedSubject)

            // Get the user ids of the old and new faculty
            val oldFacultyId = getFacultyUserId(oldFaculty) ?: -1L
            val newFacultyId = getFacultyUserId(updatedSubject.faculty) ?: -1L

            offlineUserSubjectCrossRefRepository.delete(UserSubjectCrossRef(oldFacultyId, updatedSubject.id))
            offlineUserSubjectCrossRefRepository.insert(UserSubjectCrossRef(newFacultyId, updatedSubject.id))
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