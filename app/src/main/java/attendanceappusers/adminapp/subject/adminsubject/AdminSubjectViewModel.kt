package attendanceappusers.adminapp.subject.adminsubject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.attendance.AttendanceSummary
import com.attendanceapp2.data.model.attendance.AttendanceSummaryListHolder
import com.attendanceapp2.data.model.attendance.AttendanceToExport
import com.attendanceapp2.data.model.attendance.AttendanceToExportListHolder
import com.attendanceapp2.data.model.attendance.AttendanceToExportListHolderForFaculty
import com.attendanceapp2.data.model.subject.Schedule
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OnlineAttendanceRepository
import com.attendanceapp2.data.repositories.schedule.OfflineScheduleRepository
import com.attendanceapp2.data.repositories.schedule.OnlineScheduleRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.OnlineSubjectRepository
import com.attendanceapp2.data.repositories.user.OfflineUserRepository
import com.attendanceapp2.data.repositories.user.OnlineUserRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OnlineUserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AdminSubjectViewModel(
    private val offlineUserRepository: OfflineUserRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineScheduleRepository: OfflineScheduleRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,

    private val onlineUserRepository: OnlineUserRepository,
    private val onlineSubjectRepository: OnlineSubjectRepository,
    private val onlineScheduleRepository: OnlineScheduleRepository,
    private val onlineAttendanceRepository: OnlineAttendanceRepository,
    private val onlineUserSubjectCrossRefRepository: OnlineUserSubjectCrossRefRepository
) : ViewModel() {
    private val _subjectStudents = MutableStateFlow<List<User>>(emptyList())
    val subjectStudents: StateFlow<List<User>> get() = _subjectStudents

    private val _attendanceSummaries = MutableStateFlow<Map<Int, AttendanceSummary>>(emptyMap())
    val attendanceSummaries: StateFlow<Map<Int, AttendanceSummary>> get() = _attendanceSummaries


    private val _subjectSchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val subjectSchedules: StateFlow<List<Schedule>> get() = _subjectSchedules

    private suspend fun updateOfflineSchedules() {
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

    fun getSubjectsCurrentMonthAttendances(subject: SelectedSubject) {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val startDate = currentDate.withDayOfMonth(1)
            val endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth())

            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
            val formattedStartDate = startDate.format(formatter)
            val formattedEndDate = endDate.format(formatter)

            val userSubCrossRefs = offlineUserSubjectCrossRefRepository.getUserSubjectCrossRefBySubject(subject.id)
            val userIds = userSubCrossRefs.map { it.userId }
            val students = offlineUserRepository.getUsersByIds(userIds)
            _subjectStudents.value = students
            Timber.d("Student: ${_subjectStudents.value}")

            // Iterate through each student and fetch their AttendanceSummary
            val attendanceSummaryMap = mutableMapOf<Int, AttendanceSummary>()
            students.forEach { student ->
                val summary = offlineAttendanceRepository.getAttendanceSummary(
                    student.id,
                    subject.code,
                    formattedStartDate,
                    formattedEndDate
                )
                attendanceSummaryMap[student.id] = summary
                Timber.d("Attendance summary for student with ID ${student.id}: $summary")
            }

            _attendanceSummaries.value = attendanceSummaryMap
            AttendanceSummaryListHolder.setSummaryList(attendanceSummaryMap.values.toList())
            Timber.d("Attendance summary list: ${AttendanceSummaryListHolder.getSummaryList()}")
        }
    }

    fun getSubjectsAttendancesToExport(context: Context, subject: SelectedSubject, period: String) {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

            val (startDate, endDate) = when (period) {
                "Previous Month" -> {
                    val previousMonth = currentDate.minusMonths(1)
                    val startOfPreviousMonth = previousMonth.withDayOfMonth(1)
                    val endOfPreviousMonth = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth())
                    Pair(startOfPreviousMonth.format(formatter), endOfPreviousMonth.format(formatter))
                }
                "Current Month" -> {
                    val startOfCurrentMonth = currentDate.withDayOfMonth(1)
                    val endOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
                    Pair(startOfCurrentMonth.format(formatter), endOfCurrentMonth.format(formatter))
                }
                "Whole Year" -> {
                    val startOfYear = currentDate.withDayOfYear(1)
                    val endOfYear = currentDate.withDayOfYear(currentDate.lengthOfYear())
                    Pair(startOfYear.format(formatter), endOfYear.format(formatter))
                }
                else -> Pair("", "")
            }

            val userSubCrossRefs = offlineUserSubjectCrossRefRepository.getUserSubjectCrossRefBySubject(subject.id)
            val userIds = userSubCrossRefs.map { it.userId }
            val students = offlineUserRepository.getUsersByIds(userIds)
            _subjectStudents.value = students
            Timber.d("Student: ${_subjectStudents.value}")

            // Iterate through each student and fetch their AttendanceSummary
            val attendanceToExportList = students.map { student ->
                val summary = offlineAttendanceRepository.getAttendanceSummary(
                    student.id,
                    subject.code,
                    startDate,
                    endDate
                )
                Timber.d("Attendance to export for student with ID ${student.id}: $summary")
                AttendanceToExport(
                    userId = student.id,
                    firstname = student.firstname,
                    lastname = student.lastname,
                    presentCount = summary.presentCount,
                    absentCount = summary.absentCount,
                    lateCount = summary.lateCount,
                    totalCount = summary.attendances.size,
                    attendances = summary.attendances
                )
            }

            AttendanceToExportListHolder.setAttendanceToExportList(attendanceToExportList)
            Timber.d("Attendance to export list: ${AttendanceToExportListHolder.getAttendanceToExportList()}")

            exportAttendanceSummariesAsExcel(context, period)
        }
    }

    fun getSubjectsAttendancesToExportForFaculty(context: Context, subject: SelectedSubject, period: String) {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

            val (startDate, endDate) = when (period) {
                "Previous Day" -> {
                    val previousDay = currentDate.minusDays(1)
                    Pair(previousDay.format(formatter), previousDay.format(formatter))
                }
                "Current Day" -> {
                    val today = currentDate
                    Pair(today.format(formatter), today.format(formatter))
                }
                "Previous Month" -> {
                    val previousMonth = currentDate.minusMonths(1)
                    val startOfPreviousMonth = previousMonth.withDayOfMonth(1)
                    val endOfPreviousMonth = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth())
                    Pair(startOfPreviousMonth.format(formatter), endOfPreviousMonth.format(formatter))
                }
                "Current Month" -> {
                    val startOfCurrentMonth = currentDate.withDayOfMonth(1)
                    val endOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
                    Pair(startOfCurrentMonth.format(formatter), endOfCurrentMonth.format(formatter))
                }
                "Whole Year" -> {
                    val startOfYear = currentDate.withDayOfYear(1)
                    val endOfYear = currentDate.withDayOfYear(currentDate.lengthOfYear())
                    Pair(startOfYear.format(formatter), endOfYear.format(formatter))
                }
                else -> Pair("", "")
            }

            val userSubCrossRefs = offlineUserSubjectCrossRefRepository.getUserSubjectCrossRefBySubject(subject.id)
            val userIds = userSubCrossRefs.map { it.userId }
            val students = offlineUserRepository.getUsersByIds(userIds)
            _subjectStudents.value = students
            Timber.d("Student: ${_subjectStudents.value}")

            // Iterate through each student and fetch their AttendanceSummary
            val attendanceToExportList = students.map { student ->
                val summary = offlineAttendanceRepository.getAttendanceSummary(
                    student.id,
                    subject.code,
                    startDate,
                    endDate
                )
                Timber.d("Attendance to export for student with ID ${student.id}: $summary")
                AttendanceToExport(
                    userId = student.id,
                    firstname = student.firstname,
                    lastname = student.lastname,
                    presentCount = summary.presentCount,
                    absentCount = summary.absentCount,
                    lateCount = summary.lateCount,
                    totalCount = summary.attendances.size,
                    attendances = summary.attendances
                )
            }

            AttendanceToExportListHolderForFaculty.setAttendanceToExportList(attendanceToExportList)
            Timber.d("Attendance to export list for faculty: ${AttendanceToExportListHolderForFaculty.getAttendanceToExportList()}")

            exportAttendanceSummariesAsExcelForFaculty(context, period)
        }
    }
}