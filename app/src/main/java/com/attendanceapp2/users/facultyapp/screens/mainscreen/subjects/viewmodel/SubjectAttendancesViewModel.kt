package com.attendanceapp2.users.facultyapp.screens.mainscreen.subjects.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.repositories.attendancce.AttendanceRepository
import com.attendanceapp2.data.repositories.subject.SubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import com.attendanceapp2.universaldata.SelectedSubjectHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FacultySubjectAttendancesViewModel (
    private val attendanceRepo: AttendanceRepository
) : ViewModel() {

}