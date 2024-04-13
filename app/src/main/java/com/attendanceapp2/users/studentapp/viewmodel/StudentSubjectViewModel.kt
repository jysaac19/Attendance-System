package com.attendanceapp2.users.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.authentication.LoggedInUserHolder
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.viewmodel.SubjectViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class StudentSubjectViewModel (
    private val userSubjectCrossRefRepo: UserSubjectCrossRefRepository,
    private val subjectRepo: SubjectRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

}
