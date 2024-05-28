package attendanceappusers.adminapp.subject.adminsubjectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminSubjectListViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository
) : ViewModel() {
    private val _subjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects

    init {
        updateOfflineSubjects()
    }

    private fun updateOfflineSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.deleteAllSubjects()
            val onlineSubjects = onlineSubjectRepository.getAllSubjects()
            onlineSubjects.forEach {
                offlineSubjectRepository.insertSubject(it)
            }

            offlineSubjectRepository.getActiveSubjects().collect { subjects ->
                _subjects.value = subjects
            }
        }
    }
}