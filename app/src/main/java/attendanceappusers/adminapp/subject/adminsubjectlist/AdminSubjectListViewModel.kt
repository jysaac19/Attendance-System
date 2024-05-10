package attendanceappusers.adminapp.subject.adminsubjectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminSubjectListViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {
    private val _subjects: MutableStateFlow<List<Subject>> = MutableStateFlow(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects

    init {
        getAllSubjects()
    }

    private fun getAllSubjects() {
        viewModelScope.launch {
            offlineSubjectRepository.getAllSubjects().collect { subjects ->
                _subjects.value = subjects
            }
        }
    }
}