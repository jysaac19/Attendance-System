package com.attendanceapp2.data.screen.subject

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository

class NewSubjectViewModel(
    private val offlineSubjectRepository: OfflineSubjectRepository
) : ViewModel() {

}