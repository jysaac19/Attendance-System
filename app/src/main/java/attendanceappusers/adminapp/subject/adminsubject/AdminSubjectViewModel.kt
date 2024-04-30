package attendanceappusers.adminapp.subject.adminsubject

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Schedule
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdminSubjectViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineScheduleRepository: OfflineScheduleRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
) : ViewModel() {

    private val _subjectSchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val subjectSchedules: StateFlow<List<Schedule>> get() = _subjectSchedules

    suspend fun getSubjectSchedules(subjectId: Long) {
        val schedules = offlineScheduleRepository.getSchedulesForSubject(subjectId)
        _subjectSchedules.value = schedules
    }

}