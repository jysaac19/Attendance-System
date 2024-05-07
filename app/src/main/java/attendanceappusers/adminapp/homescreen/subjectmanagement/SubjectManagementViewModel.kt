package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SubjectManagementViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository
): ViewModel() {

    private val _subjectList: MutableLiveData<List<Subject>> = MutableLiveData(emptyList())
    val subjectList: LiveData<List<Subject>> get() = _subjectList

    init {
        getAllSubjects()
    }

    fun getAllSubjects() {
        viewModelScope.launch {
            _subjectList.value = offlineSubjectRepository.getAllSubjects()
        }
    }

    fun searchSubjectsByCode(subjectCode: String) {
        viewModelScope.launch {
            if (subjectCode.isNotBlank()) {
                offlineSubjectRepository.filterSubjectList(subjectCode).collect { subjects ->
                    _subjectList.value = subjects
                }
            } else {
                // If the search query is empty, reset to all subjects
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