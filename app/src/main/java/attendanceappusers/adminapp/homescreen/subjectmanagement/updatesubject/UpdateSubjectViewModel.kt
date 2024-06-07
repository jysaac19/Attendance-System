package attendanceappusers.adminapp.homescreen.subjectmanagement.updatesubject

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.MutableStateFlow
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

    val subjectUpdated = MutableStateFlow(false)
    private suspend fun getFacultyUserId(fullName: String): Int? {
        val names = fullName.split(" ")
        val firstName = names.firstOrNull() ?: ""
        val lastName = names.drop(1).joinToString(" ")
        val faculty = offlineUserRepository.getUserByFullName(firstName, lastName)
        return faculty?.id
    }

    private fun generateJoinCode(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }

    suspend fun updateSubject(updatedSubject: Subject, oldFaculty: String) {
        viewModelScope.launch {

            val oldFacultyId = getFacultyUserId(oldFaculty)

            val newFacultyId = getFacultyUserId(updatedSubject.facultyName)

            if (oldFacultyId != null) {
                onlineUserSubjectCrossRefRepository.deleteUserSubCrossRef(
                    UserSubjectCrossRef(
                        oldFacultyId.toInt(),
                        updatedSubject.id
                    )
                )
            }
            if (newFacultyId != null) {
                onlineUserSubjectCrossRefRepository.addUserSubCrossRef(
                    UserSubjectCrossRef(
                        newFacultyId.toInt(),
                        updatedSubject.id
                    )
                )
            }

            var generatedJoinCode = generateJoinCode()
            var existingJoinCode: Subject?
            do {
                existingJoinCode = onlineSubjectRepository.getSubjectByJoinCode(generatedJoinCode)
                if (existingJoinCode != null) {
                    generatedJoinCode = generateJoinCode()
                }
            } while (existingJoinCode != null)

            onlineSubjectRepository.updateSubject(updatedSubject.copy(joinCode = generatedJoinCode))
        }
    }

    fun removeFaculty(subject : Subject){
        viewModelScope.launch {
            val facultyId = getFacultyUserId(subject.facultyName)

            if (facultyId != null) {
                onlineUserSubjectCrossRefRepository.deleteUserSubCrossRef(
                    UserSubjectCrossRef(
                        facultyId.toInt(),
                        subject.id
                    )
                )
            }

            var generatedJoinCode = generateJoinCode()
            var existingJoinCode: Subject?
            do {
                existingJoinCode = onlineSubjectRepository.getSubjectByJoinCode(generatedJoinCode)
                if (existingJoinCode != null) {
                    generatedJoinCode = generateJoinCode()
                }
            } while (existingJoinCode != null)

            onlineSubjectRepository.updateSubject(subject.copy(facultyName = "", joinCode = generatedJoinCode))
            subjectUpdated.value = true
        }
    }

    fun validateFields(subject: Subject): Results.UpdateSubjectResult {
        return when {
            subject.code.isBlank() || subject.name.isBlank() || subject.subjectStatus.isBlank() || subject.joinCode.isBlank()
            -> Results.UpdateSubjectResult(failureMessage = "Subject Code and Name are Required.")

            else -> Results.UpdateSubjectResult(successMessage = "Fields are valid.")
        }
    }
}