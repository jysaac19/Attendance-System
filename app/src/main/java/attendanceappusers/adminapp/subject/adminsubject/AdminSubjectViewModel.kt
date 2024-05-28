package attendanceappusers.adminapp.subject.adminsubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.schedule.OnlineScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminSubjectViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineScheduleRepository: OfflineScheduleRepository,
    private val onlineScheduleRepository: OnlineScheduleRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
) : ViewModel() {

    private val _subjectSchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val subjectSchedules: StateFlow<List<Schedule>> get() = _subjectSchedules

    suspend fun updateOfflineSchedules() {
        viewModelScope.launch {
            offlineScheduleRepository.deleteAllSchedules()
            val onlineSchedules = onlineScheduleRepository.getAllSchedules()
            onlineSchedules.forEach {
                offlineScheduleRepository.insertSchedule(it)
            }
        }
    }

    suspend fun getSchedulesForSubjects(subjectId: Int) {
        viewModelScope.launch {
            offlineScheduleRepository.deleteAllSchedules()
            val onlineSchedules = onlineScheduleRepository.getAllSchedules()
            onlineSchedules.forEach {
                offlineScheduleRepository.insertSchedule(it)
            }

            val schedules = offlineScheduleRepository.getSchedulesForSubject(subjectId)
            _subjectSchedules.value = sortSchedules(schedules)
        }
    }

    suspend fun addScheduleOnline(schedule: Schedule): Results.AddScheduleResult {
        updateOfflineSchedules()

        if (schedule.day.isBlank() || schedule.start.isBlank() || schedule.end.isBlank()) {
            return Results.AddScheduleResult(failureMessage = "Schedule fields must not be empty")
        }

        val existingSchedules = offlineScheduleRepository.getSchedulesForSubject(schedule.subjectId)

        if (existingSchedules.any { it.day == schedule.day && it.start == schedule.start && it.end == schedule.end }) {
            return Results.AddScheduleResult(failureMessage ="Schedule already exists")
        }

        if (existingSchedules.any { it.day == schedule.day && isOverlapping(it, schedule) }) {
            return Results.AddScheduleResult(failureMessage ="Schedule overlaps with an existing schedule")
        }

        onlineScheduleRepository.addSchedule(schedule)

        _subjectSchedules.value = sortSchedules(existingSchedules + schedule)
        return Results.AddScheduleResult(successMessage = "Schedule Added Successfully")
    }

    private fun isOverlapping(existingSchedule: Schedule, newSchedule: Schedule): Boolean {
        val existingStart = existingSchedule.start.toMinutes()
        val existingEnd = existingSchedule.end.toMinutes()
        val newStart = newSchedule.start.toMinutes()
        val newEnd = newSchedule.end.toMinutes()

        return newStart < existingEnd && newEnd > existingStart
    }

    suspend fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch {
            onlineScheduleRepository.deleteSchedule(schedule.id)
            updateOfflineSchedules()

            val updatedSchedules = offlineScheduleRepository.getSchedulesForSubject(schedule.subjectId)
            _subjectSchedules.value = sortSchedules(updatedSchedules)
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