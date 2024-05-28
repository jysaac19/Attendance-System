package attendanceappusers.adminapp.homescreen.subjectmanagement.addsubject

import androidx.lifecycle.ViewModel
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
import timber.log.Timber

class AddSubjectViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository,
) : ViewModel() {
    val facultyList: Flow<List<User>> = offlineUserRepository.getUsersByUserType("Faculty")

    private fun generateJoinCode(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private suspend fun getFacultyUserId(fullName: String): Int? {
        val names = fullName.split(" ")
        val firstName = names.firstOrNull() ?: ""
        val lastName = names.drop(1).joinToString(" ")
        val faculty = onlineUserRepository.getUserByFullName(firstName, lastName)
        return faculty?.id
    }

    suspend fun saveSubject(subjectCode: String, subjectName: String, room: String, faculty: String): Results.AddSubjectResult {
        return try {
            if (subjectCode.isBlank() || subjectName.isBlank()) {
                return Results.AddSubjectResult(failureMessage = "Subject code and name cannot be empty.")
            }

            val existingSubjectCode = onlineSubjectRepository.getSubjectByCode(subjectCode)
            if (existingSubjectCode != null) {
                return Results.AddSubjectResult(failureMessage = "Subject with code $subjectCode already exists.")
            }

            val existingSubjectName = onlineSubjectRepository.getSubjectByName(subjectName)
            if (existingSubjectName != null) {
                return Results.AddSubjectResult(failureMessage = "Subject with name $subjectName already exists.")
            }

            var generatedJoinCode = generateJoinCode()
            var existingJoinCode: Subject?
            do {
                existingJoinCode = onlineSubjectRepository.getSubjectByJoinCode(generatedJoinCode)
                if (existingJoinCode != null) {
                    generatedJoinCode = generateJoinCode()
                }
            } while (existingJoinCode != null)

            val newSubject = Subject(
                code = subjectCode,
                name = subjectName,
                room = room,
                facultyName = faculty,
                subjectStatus = "Active",
                joinCode = generatedJoinCode
            )

            onlineSubjectRepository.addSubject(newSubject)

            println("Inserted Subject: $newSubject")

            val insertedSubject = onlineSubjectRepository.getSubjectByCode(subjectCode)
            val subjectId = insertedSubject!!.id

            val facultyUserId = getFacultyUserId(faculty) ?: return Results.AddSubjectResult(failureMessage = "Failed to retrieve facultyUserId.")

            onlineUserSubjectCrossRefRepository.addUserSubCrossRef(UserSubjectCrossRef(facultyUserId, subjectId))

            Results.AddSubjectResult(successMessage = "Subject added successfully. SubjectId: $subjectId")
        } catch (e: Exception) {
            Timber.e(e, "Error saving subject")
            Results.AddSubjectResult(failureMessage = "Failed to save subject.")
        }
    }
}