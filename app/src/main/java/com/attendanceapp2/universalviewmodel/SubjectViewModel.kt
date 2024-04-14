package com.attendanceapp2.universalviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.universaldata.LoggedInUserHolder
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubjectViewModel(
    private val userSubjectCrossRefRepo: UserSubjectCrossRefRepository,
    private val subjectRepo: SubjectRepository
) : ViewModel() {

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList()) // LiveData or StateFlow for subjects
    val subjects = _subjects.asStateFlow()

    // Function to fetch subjects associated with the logged-in user
    fun fetchSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            viewModelScope.launch {
                val userId = user.userId
                val subjectIds = userSubjectCrossRefRepo.getSubjectIdsForUser(userId)

                // Fetch subjects using subjectIds
                val subjects = subjectRepo.getSubjectsByIds(subjectIds)
                _subjects.value = subjects ?: emptyList()

                // Log the fetched subjects
                subjects?.let {
                    if (it.isNotEmpty()) {
                        println("Fetched subjects: $subjects")
                    } else {
                        println("No subjects fetched.")
                    }
                } ?: println("Failed to fetch subjects.")
            }
        }
    }
}