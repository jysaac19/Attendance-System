package com.attendanceapp2.authentication

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository

sealed class SignUpResult {
    data class Success(val user: User) : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}

class SignUpViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val onlineUserRepository: OnlineUserRepository
) : ViewModel() {
    suspend fun signUp(firstName: String, lastName: String, email: String, password: String, reEnterPassword: String, userType: String, department: String, status: String): SignUpResult {
        return try {
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
                return SignUpResult.Error("Please fill in all fields")
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return SignUpResult.Error("Invalid email format")
            }

            if (password != reEnterPassword) {
                return SignUpResult.Error("Passwords do not match")
            }

            val existingUser = try {
                onlineUserRepository.getUserByEmail(email)
            } catch (e: Exception) {
                null
            }

            if (existingUser != null) {
                return SignUpResult.Error("Email already exists")
            }

            val newUser = User(0, firstName, lastName, email, password, userType, department, status)
            try {
                onlineUserRepository.addUser(newUser)
            } catch (e: Exception) {
                return SignUpResult.Error("Failed to add user: ${e.message}")
            }

            SignUpResult.Success(newUser)
        } catch (e: Exception) {
            SignUpResult.Error("An unexpected error occurred: ${e.message}")
        }
    }
}