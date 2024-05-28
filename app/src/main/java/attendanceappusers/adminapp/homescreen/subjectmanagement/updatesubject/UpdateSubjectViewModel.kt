package attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UpdateSubjectViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,
) : ViewModel() {
    val facultyList: Flow<List<User>> = offlineUserRepository.getUsersByUserType("Faculty")

    private suspend fun getFacultyUserId(fullName: String): Int? {
        val names = fullName.split(" ")
        val firstName = names.firstOrNull() ?: ""
        val lastName = names.drop(1).joinToString(" ")
        val faculty = offlineUserRepository.getUserByFullName(firstName, lastName)
        return faculty?.id
    }

    suspend fun updateSubject(updatedSubject: Subject, oldFaculty: String) {
        viewModelScope.launch {
            try {
                onlineSubjectRepository.updateSubject(updatedSubject)

                val oldFacultyId = try {
                    getFacultyUserId(oldFaculty)
                } catch (e: Exception) {
                    null
                }

                val newFacultyId = try {
                    getFacultyUserId(updatedSubject.facultyName)
                } catch (e: Exception) {
                    null
                }

                try {
                    if (oldFacultyId != null) {
                        onlineUserSubjectCrossRefRepository.deleteUserSubCrossRef(UserSubjectCrossRef(oldFacultyId.toInt(), updatedSubject.id))
                    }
                } catch (e: Exception) {
                    // Handle exception
                }

                try {
                    if (newFacultyId != null) {
                        onlineUserSubjectCrossRefRepository.addUserSubCrossRef(UserSubjectCrossRef(newFacultyId.toInt(), updatedSubject.id))
                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            } catch (e: Exception) {
                // Handle any other exceptions
            }
        }
    }

    fun validateFields(subject: Subject): Results.UpdateSubjectResult {
        return when {
            subject.code.isBlank() || subject.name.isBlank() || subject.room.isBlank() ||
            subject.facultyName.isBlank() || subject.subjectStatus.isBlank() || subject.joinCode.isBlank()
            -> Results.UpdateSubjectResult(failureMessage = "All fields are required.")

            else -> Results.UpdateSubjectResult(successMessage = "Fields are valid.")
        }
    }
}