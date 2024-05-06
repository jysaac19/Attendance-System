package attendanceappusers.adminapp.homescreen.usermanagement.updateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import kotlinx.coroutines.launch

class UpdateUserViewModel(
    private val offlineUserRepository: OfflineUserRepository
): ViewModel() {
    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            offlineUserRepository.updateStudent(updatedUser)
        }
    }

    fun validateFields(user: User): Results.UpdateUserResult {
        return when {
            user.firstname.isBlank() || user.lastname.isBlank() || user.email.isBlank() ||
                    user.password.isBlank() || user.usertype.isBlank() || user.department.isBlank() ||
                    user.status.isBlank() -> Results.UpdateUserResult(failureMessage = "All fields are required.")
            user.password.length < 8 -> Results.UpdateUserResult(failureMessage = "Password must be at least 8 characters long.")
            else -> Results.UpdateUserResult(successMessage = "Fields are valid.")
        }
    }
}