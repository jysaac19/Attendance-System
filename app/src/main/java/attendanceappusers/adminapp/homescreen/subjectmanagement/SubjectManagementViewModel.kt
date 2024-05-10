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

    private val _subjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects


    init {
        getAllSubjects()
    }

    fun getAllSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.getAllSubjects().collect { subjects ->
                _subjects.value = subjects
            }
        }
    }

    fun searchSubjectsByCode(subjectCode: String) {
        viewModelScope.launch {
            if (subjectCode.isNotEmpty()) {
                offlineSubjectRepository.searchSubject(subjectCode).collect { subjects ->
                    _subjects.value = subjects
                }
            } else if (subjectCode.isEmpty()) {
                getAllSubjects()
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
}