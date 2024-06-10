package com.attendanceapp2.appviewmodel.screenviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubjectViewModel(
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,

    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository
) : ViewModel() {

    private val _activeSubjects = MutableStateFlow<List<Subject>>(emptyList())
    val activeSubjects = _activeSubjects.asStateFlow()

    private val _archivedSubjects = MutableStateFlow<List<Subject>>(emptyList())
    val archivedSubjects = _archivedSubjects.asStateFlow()

    fun fetchActiveSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            offlineUserSubjectCrossRefRepository.deleteAllUserSubjectCrossRefs()
            offlineSubjectRepository.deleteAllSubjects()

            val onlineUserSubjectCrossRefs = onlineUserSubjectCrossRefRepository.getAllUserSubCrossRef()
            val onlineSubjects = onlineSubjectRepository.getAllSubjects()

            onlineUserSubjectCrossRefs.forEach { offlineUserSubjectCrossRefRepository.insert(it)}
            onlineSubjects.forEach { offlineSubjectRepository.insertSubject(it)}

            val userId = loggedInUser!!.id
            val userSubjectCrossRefs = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForUser(userId)
            val subjectIds = userSubjectCrossRefs.map { it.subjectId }

            val activeSubjects = offlineSubjectRepository.getActiveSubjectsByIds(subjectIds).sortedBy { it.code }
            _activeSubjects.value = activeSubjects

            if (activeSubjects.isNotEmpty()) {
                println("Fetched subjects: $activeSubjects")
            } else {
                println("No subjects fetched.")
            }
        }
    }

    fun fetchArchivedSubjectsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        viewModelScope.launch {
            offlineUserSubjectCrossRefRepository.deleteAllUserSubjectCrossRefs()
            offlineSubjectRepository.deleteAllSubjects()

            val onlineUserSubjectCrossRefs = onlineUserSubjectCrossRefRepository.getAllUserSubCrossRef()
            val onlineSubjects = onlineSubjectRepository.getAllSubjects()

            onlineUserSubjectCrossRefs.forEach { offlineUserSubjectCrossRefRepository.insert(it)}
            onlineSubjects.forEach { offlineSubjectRepository.insertSubject(it)}

            val userId = loggedInUser!!.id
            val userSubjectCrossRefs = offlineUserSubjectCrossRefRepository.getJoinedSubjectsForUser(userId)
            val subjectIds = userSubjectCrossRefs.map { it.subjectId }

            val archivedSubjects = offlineSubjectRepository.getArchivedSubjectsByIds(subjectIds).sortedBy { it.code }
            _archivedSubjects.value = archivedSubjects

            if (archivedSubjects.isNotEmpty()) {
                println("Fetched subjects: $archivedSubjects")
            } else {
                println("No subjects fetched.")
            }
        }
    }
}