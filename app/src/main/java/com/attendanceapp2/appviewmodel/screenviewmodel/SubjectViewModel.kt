package com.attendanceapp2.appviewmodel.screenviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubjectViewModel(
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects = _subjects.asStateFlow()

    fun fetchSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            val userId = loggedInUser!!.id

            val userSubjectCrossRefs = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForUser(userId)

            val subjectIds = userSubjectCrossRefs.map { it.subjectId }

            val subjects = offlineSubjectRepository.getSubjectsByIds(subjectIds)

            _subjects.value = subjects

            if (subjects.isNotEmpty()) {
                println("Fetched subjects: $subjects")
            } else {
                println("No subjects fetched.")
            }
        }
    }
}