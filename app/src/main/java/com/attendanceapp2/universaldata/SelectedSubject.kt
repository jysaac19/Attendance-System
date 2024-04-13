package com.attendanceapp2.universaldata

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SelectedSubject(
    val id : Long,
    val code : String,
    val name : String,
    val room : String,
    val faculty : String,
    val day : String,
    val start : String,
    val end : String
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