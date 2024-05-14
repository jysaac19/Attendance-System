package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectManagementViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository
): ViewModel() {

    private val _activeSubjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val activeSubjects: StateFlow<List<Subject>> = _activeSubjects

    private val _archivedSubjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val archivedSubjects: StateFlow<List<Subject>> = _archivedSubjects

    init {
        updateSubjectLists()
    }

    private fun getActiveSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.getActiveSubjects().collect { subjects ->
                _activeSubjects.value = subjects
            }
        }
    }

    private fun getArchivedSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.getArchivedSubjects().collect { subjects ->
                _archivedSubjects.value = subjects
            }
        }
    }

    fun updateSubjectLists() {
        getActiveSubjects()
        getArchivedSubjects()
    }

    fun searchSubjectsByCode(subjectCode: String) {
        viewModelScope.launch {
            if (subjectCode.isNotEmpty()) {
                offlineSubjectRepository.searchSubject(subjectCode).collect { subjects ->
                    _activeSubjects.value = subjects
                }
                offlineSubjectRepository.searchSubject(subjectCode).collect { subjects ->
                    _activeSubjects.value = subjects
                }
            } else if (subjectCode.isEmpty()) {
                getActiveSubjects()
                getArchivedSubjects()
            }
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            offlineSubjectRepository.deleteSubject(subject)
        }
    }

    fun archiveSubject(subject: Subject) {
        viewModelScope.launch {
            // Update the subject status to "Archived"
            val archivedSubject = subject.copy(subjectStatus = "Archived")
            offlineSubjectRepository.updateSubject(archivedSubject)
        }
    }

    fun unarchiveSubject(subject: Subject) {
        viewModelScope.launch {
            // Update the subject status to "Archived"
            val archivedSubject = subject.copy(subjectStatus = "Active")
            offlineSubjectRepository.updateSubject(archivedSubject)
        }
    }
}