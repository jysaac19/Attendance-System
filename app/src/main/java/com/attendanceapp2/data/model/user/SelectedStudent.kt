package com.attendanceapp2.data.model.user

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SelectedStudent(
    val id : Int,
    val firstname : String,
    val lastname : String,
    val email : String, //should be @student.nbscollege.edu.ph
    val password : String,
    val usertype : String, //Admin, Student, Faculty
    val department : String, //BSCS, BSA, BSE, BSAIS, BSTM
    val status : String //Active, Inactive
)

object SelectedStudentHolder {
    private val _selectedStudent = MutableStateFlow<SelectedStudent?>(null)
    val selectedStudent = _selectedStudent.asStateFlow()

    fun setSelectedStudent(student: SelectedStudent) {
        _selectedStudent.value = student
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }

    fun getSelectedStudent(): SelectedStudent? {
        return _selectedStudent.value
    }
}