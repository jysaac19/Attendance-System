package com.attendanceapp2.data.model.subject

import androidx.room.ColumnInfo
import com.attendanceapp2.data.model.user.UpdateUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UpdateSubject(
    val id : Int = 0,
    val code : String,
    val name : String,
    val room : String,
    val faculty : String,
    val subjectStatus: String, //Active, Archived
    val joinCode: String
)

object UpdatingSubjectHolder {
    private val _updatingSubject = MutableStateFlow<UpdateSubject?>(null)
    val updatingSubject = _updatingSubject.asStateFlow()

    fun setSelectedSubject(subject: UpdateSubject) {
        _updatingSubject.value = subject
    }

    fun clearSelectedSubject() {
        _updatingSubject.value = null
    }

    fun getSelectedSubject(): UpdateSubject? {
        return _updatingSubject.value
    }
}