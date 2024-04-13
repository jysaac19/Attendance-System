package com.attendanceapp2.users.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.viewmodel.SubjectViewModel


class StudentSubjectViewModel (
    private val userSubjectCrossRefRepo: UserSubjectCrossRefRepository,
    private val subjectRepo: SubjectRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

}
