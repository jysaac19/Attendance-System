package com.attendanceapp2.screenuniversalcomponents.schedule

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.schedule.ScheduleRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository

class NewScheduleViewModel(
    private val subjectRepo: SubjectRepository,
    private val scheduleRepo: ScheduleRepository
) : ViewModel() {

}