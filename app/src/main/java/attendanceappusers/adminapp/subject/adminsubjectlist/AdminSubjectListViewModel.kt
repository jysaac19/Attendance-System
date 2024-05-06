package attendanceappusers.adminapp.subject.adminsubjectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.launch

class AdminSubjectListViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {
    private var _subjects: List<Subject> = emptyList()
    val subjects: List<Subject>
        get() = _subjects

    init {
        getAllSubjects()
    }

    private fun getAllSubjects() {
        viewModelScope.launch {
            _subjects = offlineSubjectRepository.getAllSubjects()
        }
    }
}