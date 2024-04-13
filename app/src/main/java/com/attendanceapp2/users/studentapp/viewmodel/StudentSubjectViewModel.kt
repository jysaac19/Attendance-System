package com.attendanceapp2.users.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.authentication.LoggedInUserHolder
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SubjectViewModel(private val userSubjectCrossRefRepository: UserSubjectCrossRefRepository) : ViewModel() {

    // LiveData or StateFlow to hold the subject IDs
    // You can use MutableLiveData or MutableStateFlow depending on your preference
    // Here, I'll use MutableStateFlow
    private val _subjectIds = MutableStateFlow<List<Long>>(emptyList())
    val subjectIds = _subjectIds.asStateFlow()

    // Function to fetch subject IDs associated with the logged-in user
    fun fetchSubjectIdsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            viewModelScope.launch {
                val userId = user.userId
                val subjectIds = userSubjectCrossRefRepository.getSubjectIdsForUser(userId)
                _subjectIds.value = subjectIds ?: emptyList()

                // Log the fetched subject IDs
                subjectIds.let {
                    if (it.isNotEmpty()) {
                        val subjectIdsString = it.joinToString(", ")
                        println("Fetched subject IDs: $subjectIdsString")
                    } else {
                        println("No subject IDs fetched.")
                    }
                } ?: println("Failed to fetch subject IDs.")
            }
        }
    }
}