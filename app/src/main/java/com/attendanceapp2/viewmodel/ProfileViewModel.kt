package com.attendanceapp2.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.authentication.LoggedInUserHolder

class ProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
    }
}