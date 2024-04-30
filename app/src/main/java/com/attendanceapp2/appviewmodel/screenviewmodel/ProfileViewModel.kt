package com.attendanceapp2.appviewmodel.screenviewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.LoggedInUserHolder
import com.attendanceapp2.data.model.SelectedSubjectHolder

class ProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
        SelectedSubjectHolder.clearSelectedSubject()
    }
}