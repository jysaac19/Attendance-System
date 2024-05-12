package attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddSubjectViewModel(
    private val userRepository: OfflineUserRepository,
    private val userSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val subjectRepository: OfflineSubjectRepository,
): ViewModel() {
    val facultyList: Flow<List<User>> = userRepository.getUsersByUserType("Faculty")

    private fun generateJoinCode(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private suspend fun getFacultyUserId(fullName: String): Long? {
        val names = fullName.split(" ")
        val firstName = names.firstOrNull() ?: ""
        val lastName = names.drop(1).joinToString(" ")
        val faculty = userRepository.getUserByFullName(firstName, lastName)
        return faculty?.id
    }

    private suspend fun insertUserSubjectCrossRef(userId: Long, subjectId: Long) {
        userSubjectCrossRefRepository.insert(UserSubjectCrossRef(userId, subjectId))
    }

    suspend fun saveSubject(subjectCode: String, subjectName: String, room: String, faculty: String): Results.AddSubjectResult {
        // Check if subject code and subject name are not empty
        if (subjectCode.isBlank() || subjectName.isBlank()) {
            return Results.AddSubjectResult(failureMessage = "Subject code and name cannot be empty.")
        }

        // Check if subject code already exists
        if (subjectRepository.getActiveSubjectByCode(subjectCode) != null) {
            return Results.AddSubjectResult(failureMessage = "Subject with code $subjectCode already exists.")
        }

        // Check if subject name already exists
        if (subjectRepository.getActiveSubjectByName(subjectName) != null) {
            return Results.AddSubjectResult(failureMessage = "Subject with name $subjectName already exists.")
        }

        // Generate a unique join code
        var generatedJoinCode = generateJoinCode()
        // Check if the generated join code already exists
        while (subjectRepository.getSubjectByJoinCode(generatedJoinCode) != null) {
            // Regenerate join code until it's unique
            generatedJoinCode = generateJoinCode()
        }
        // Create the new subject object with the generated join code
        val newSubject = Subject(
            code = subjectCode,
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

        // Retrieve the subjectId of the inserted subject
        val insertedSubject = subjectRepository.getActiveSubjectByCode(subjectCode)
        val subjectId = insertedSubject?.id ?: return Results.AddSubjectResult(failureMessage = "Failed to retrieve subjectId.")

        // Retrieve the userId of the selected faculty
        val facultyUserId = getFacultyUserId(faculty) ?: return Results.AddSubjectResult(failureMessage = "Failed to retrieve facultyUserId.")

        // Insert user subject cross-reference
        insertUserSubjectCrossRef(facultyUserId, subjectId)

        // Return success message with subjectId
        return Results.AddSubjectResult(successMessage = "Subject added successfully. SubjectId: $subjectId")
    }

    private fun generateRandomCode(): String {
        // Generate a random 6-character alphanumeric code
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }
}