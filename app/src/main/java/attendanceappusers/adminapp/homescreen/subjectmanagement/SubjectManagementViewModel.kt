package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SubjectManagementViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository,
    private val onlineUserRepository: OnlineUserRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository,
) : ViewModel() {

    private val _activeSubjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val activeSubjects: StateFlow<List<Subject>> = _activeSubjects

    private val _archivedSubjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val archivedSubjects: StateFlow<List<Subject>> = _archivedSubjects

    init {
        updateSubjectManagementList()
    }


    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val subjects = onlineSubjectRepository.getAllSubjects()
            subjects.forEach {
                offlineSubjectRepository.insertSubject(it)
            }
        }
    }

    private fun updateSubjectManagementList() {
        viewModelScope.launch {
            updateOfflineSubjects()
            fetchArchiveSubjects()
            fetchActiveSubjects()
        }
    }

    private fun fetchArchiveSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.getArchivedSubjects().collect {
                _archivedSubjects.value = it
            }
        }
    }

    private fun fetchActiveSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.getActiveSubjects().collect {
                _activeSubjects.value = it
            }
        }
    }


    fun searchSubjectsByCode(searchText: String) {
        viewModelScope.launch {
            if (searchText.isNotEmpty()) {
                offlineSubjectRepository.searchActiveSubject(searchText).collect { subjects ->
                    _activeSubjects.value = subjects
                }
                offlineSubjectRepository.searchArchivedSubject(searchText).collect { subjects ->
                    _archivedSubjects.value = subjects
                }
            } else if (searchText.isEmpty()) {
                updateSubjectManagementList()
            }
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            val names = subject.facultyName.split(" ")
            val firstName = names.firstOrNull() ?: ""
            val lastName = names.drop(1).joinToString(" ")
            val faculty = onlineUserRepository.getUserByFullName(firstName, lastName)

            onlineSubjectRepository.deleteSubject(subject.id)
            onlineUserSubjectCrossRefRepository.deleteUserSubCrossRef(UserSubjectCrossRef(faculty!!.id, subject.id))
            updateSubjectManagementList()
        }
    }

    fun archiveSubject(subject: Subject) {
        viewModelScope.launch {
            val archivedSubject = subject.copy(subjectStatus = "Archived")
            onlineSubjectRepository.updateSubject(archivedSubject)
            updateSubjectManagementList()
        }
    }

    fun unarchiveSubject(subject: Subject) {
        viewModelScope.launch {
            val activeSubject = subject.copy(subjectStatus = "Active")
            onlineSubjectRepository.updateSubject(activeSubject)
            updateSubjectManagementList()
        }
    }
}