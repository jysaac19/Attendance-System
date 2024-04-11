package com.attendanceapp2.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.user.UserRepository
import com.attendanceapp2.viewmodel.LoggedInUserHolder

class ProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
    }
}