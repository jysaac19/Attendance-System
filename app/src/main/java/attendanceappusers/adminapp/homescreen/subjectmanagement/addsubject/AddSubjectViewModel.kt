package attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddSubjectViewModel(
    private val userRepository: OfflineUserRepository,
    private val userSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val subjectRepository: OfflineSubjectRepository,
): ViewModel() {
    val facultyList: Flow<List<User>> = userRepository.getUsersByUserType("Faculty")

    private val _saveSubjectResult = MutableStateFlow<Results?>(null)
    val saveSubjectResult: StateFlow<Results?> = _saveSubjectResult

    private fun generateJoinCode(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }


    fun saveSubject(subjectCode: String, subjectName: String, room: String, faculty: String) {
        viewModelScope.launch {
            // Check if subject code already exists
            if (subjectRepository.getActiveSubjectByCode(subjectCode) != null) {
                _saveSubjectResult.value = Results.AddSubjectResult(failureMessage = "Subject with code $subjectCode already exists.")
                return@launch
            }

            // Check if subject name already exists
            if (subjectRepository.getActiveSubjectByName(subjectName) != null) {
                _saveSubjectResult.value = Results.AddSubjectResult(failureMessage = "Subject with name $subjectName already exists.")
                return@launch
            }

            // Generate a unique join code
            var generatedJoinCode = generateJoinCode()
            var newSubjectCode = subjectCode
            // Check if the generated join code already exists
            while (subjectRepository.getSubjectByJoinCode(generatedJoinCode) != null) {
                // Regenerate join code until it's unique
                generatedJoinCode = generateJoinCode()
            }
            // Create the new subject object with the generated join code
            val newSubject = Subject(
                code = newSubjectCode,
                name = subjectName,
                room = room,
                faculty = faculty,
                subjectStatus = "Active",
                joinCode = generatedJoinCode
            )
            // Insert the new subject into the repository
            subjectRepository.insertSubject(newSubject)
            // Display the inserted subject in Logcat
            println("Inserted Subject: $newSubject")
            _saveSubjectResult.value = Results.AddSubjectResult(successMessage = "Subject added successfully.")
        }
    }

    private suspend fun generateUniqueSubjectCode(): String {
        var generatedJoinCode = generateRandomCode()
        while (subjectRepository.getSubjectByJoinCode(generatedJoinCode) != null) {
            generatedJoinCode = generateRandomCode()
        }
        return generatedJoinCode
    }

    private fun generateRandomCode(): String {
        // Generate a random 6-character alphanumeric code
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }
}