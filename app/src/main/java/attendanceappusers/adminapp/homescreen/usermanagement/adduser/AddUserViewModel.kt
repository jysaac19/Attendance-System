package attendanceappusers.adminapp.homescreen.usermanagement.adduser

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AddUserViewModel (
    private val offlineUserRepository: OfflineUserRepository,
) : ViewModel() {
    suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        usertype: String,
        department: String,
        status: String
    ): Results.AddUserResult {
        // Check if firstname, lastname, and email are unique
        if (offlineUserRepository.getUserByEmail(email) != null) {
            return Results.AddUserResult(failureMessage = "User with this email already exists")
        }

        // Check if password meets the length requirement
        if (password.length < 8) {
            return Results.AddUserResult(failureMessage = "Password must be at least 8 characters long")
        }

        // Check if firstname and lastname combination is unique
        if (offlineUserRepository.getUserByFullName(firstname, lastname) != null) {
            return Results.AddUserResult(failureMessage = "User with this firstname and lastname already exists")
        }

        // Insert the new user into the database
        offlineUserRepository.insertStudent(
            User(
                id = 0, // Auto-generated ID
                firstname = firstname,
                lastname = lastname,
                email = email,
                password = password,
                usertype = usertype,
                department = department,
                status = status
            )
        )

        return Results.AddUserResult(successMessage = "User added successfully")
    }
}