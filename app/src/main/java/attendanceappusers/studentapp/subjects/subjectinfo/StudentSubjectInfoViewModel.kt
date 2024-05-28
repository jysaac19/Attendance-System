package attendanceappusers.studentapp.subjects.subjectinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.schedule.OnlineScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentSubjectInfoViewModel(
    private val offlineScheduleRepository: OfflineScheduleRepository,
    private val onlineScheduleRepository: OnlineScheduleRepository
): ViewModel() {

    private val _subjectSchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val subjectSchedules: StateFlow<List<Schedule>> get() = _subjectSchedules

    suspend fun updateOfflineSchedules(subjectId: Int) {
        viewModelScope.launch {
            offlineScheduleRepository.deleteAllSchedules()

            val onlineSchedules = onlineScheduleRepository.getAllSchedules()
            onlineSchedules.forEach {
                offlineScheduleRepository.insertSchedule(it)
            }

            val offlineSchedules = offlineScheduleRepository.getSchedulesForSubject(subjectId)
            _subjectSchedules.value = sortSchedules(offlineSchedules)
        }
    }

    private fun sortSchedules(schedules: List<Schedule>): List<Schedule> {
        val dayOrder = listOf("Weekdays", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

        return schedules.sortedWith(
            compareBy({ dayOrder.indexOf(it.day) }, { it.start.toHours() }, { it.start.toMinutes() })
        )
    }

    private fun String.toMinutes(): Int {
        val (hours, minutes, period) = split(":").map { it.trim() }
        val hour = if (hours.toInt() == 12) 0 else hours.toInt()
        val totalMinutes = hour * 60 + minutes.toInt()
        return if (period.equals("PM", ignoreCase = true)) totalMinutes + 12 * 60 else totalMinutes
    }

    private fun String.toHours(): Int {
        val (hours, minutes, period) = split(":").map { it.trim() }
        val hour = if (hours.toInt() == 12) 0 else hours.toInt()
        return if (period.equals("PM", ignoreCase = true)) hour + 12 else hour
    }
}