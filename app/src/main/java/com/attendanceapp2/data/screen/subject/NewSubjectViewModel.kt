package com.attendanceapp2.data.screen.subject

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.schedule.ScheduleRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository

class NewSubjectViewModel(
    private val subjectRepo: SubjectRepository,
    private val scheduleRepo: ScheduleRepository
) : ViewModel() {

}