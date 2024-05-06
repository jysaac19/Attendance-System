package com.attendanceapp2.appviewmodel.screenviewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder

class ProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
        SelectedSubjectHolder.clearSelectedSubject()
    }
}