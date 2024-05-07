package com.attendanceapp2.data.model.subject

import androidx.room.ColumnInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SelectedSubject(
    val id : Long = 0,
    val code : String,
    val name : String,
    val room : String,
    val faculty : String,
    val subjectStatus: String, //Active, Archived
    val joinCode: String
)

object SelectedSubjectHolder {
    private val _selectedSubject = MutableStateFlow<SelectedSubject?>(null)
    val selectedSubject = _selectedSubject.asStateFlow()

    fun setSelectedSubject(subject: SelectedSubject) {
        _selectedSubject.value = subject
    }

    fun clearSelectedSubject() {
        _selectedSubject.value = null
    }

    fun getSelectedSubject(): SelectedSubject? {
        return _selectedSubject.value
    }
}