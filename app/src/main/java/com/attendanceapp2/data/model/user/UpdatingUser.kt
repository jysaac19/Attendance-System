package com.attendanceapp2.data.model.user

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UpdateUser (
    val id : Long,
    val firstname : String,
    val lastname : String,
    val email : String, //should be @student.nbscollege.edu.ph
    val password : String,
    val usertype : String, //Admin, Student, Faculty
    val department : String, //BSCS, BSA, BSE, BSAIS, BSTM
    val status : String //Active, Inactive
)

object UpdatingUserHolder {
    private val _updatingUser = MutableStateFlow<UpdateUser?>(null)
    val updatingUser = _updatingUser.asStateFlow()

    fun setSelectedUser(user: UpdateUser) {
        _updatingUser.value = user
    }

    fun clearSelectedUser() {
        _updatingUser.value = null
    }

    fun getSelectedUser(): UpdateUser? {
        return _updatingUser.value
    }
}
