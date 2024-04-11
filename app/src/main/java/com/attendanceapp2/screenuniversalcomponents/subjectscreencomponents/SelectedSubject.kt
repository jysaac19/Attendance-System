package com.attendanceapp2.screenuniversalcomponents.subjectscreencomponents

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SelectedSubject(
    val id : Long,
    val code : String,
    val name : String,
    val room : String,
    val faculty : String
)


object SelectedSubjectHolder {
    private val _selectedSubject = MutableStateFlow<SelectedSubject?>(null)
    val selectedSubject = _selectedSubject.asStateFlow()

    fun setSelectedSubject(subject: SelectedSubject) {
        if (_selectedSubject.value != subject) {
            _selectedSubject.value = subject
        }
    }

    fun clearSelectedSubject() {
        _selectedSubject.value = null
    }

    fun getSelectedSubject(): SelectedSubject? {
        return _selectedSubject.value
    }
}