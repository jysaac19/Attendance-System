package com.attendanceapp2.universal.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.universal.data.LoggedInUserHolder

class ProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
    }
}