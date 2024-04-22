package com.attendanceapp2.user.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.universal.viewmodel.SubjectViewModel


class StudentSubjectViewModel (
    private val userSubjectCrossRefRepo: UserSubjectCrossRefRepository,
    private val subjectRepo: SubjectRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

}
