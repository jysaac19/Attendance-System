package attendanceappusers.adminapp.homescreen.subjectmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SubjectManagementViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository
): ViewModel() {

    private val _subjectList: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val subjectList: MutableStateFlow<List<Subject>> get() = _subjectList

    init {
        getAllSubjects()
    }

    private fun getAllSubjects() {
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
}