package com.attendanceapp2.universal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ScreenViewModel : ViewModel() {
    private val _splashLoaded = MutableStateFlow(false)
    val splashLoaded = _splashLoaded.asStateFlow()

    private val _isLoggedin = MutableStateFlow(false)
    val isLoggedin = _isLoggedin.asStateFlow()

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut = _isLoggedOut.asStateFlow()

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered = _isRegistered.asStateFlow()

    private val _subjectRegistered = MutableStateFlow(false)
    val subjectRegistered = _subjectRegistered.asStateFlow()

    fun runSplashScreen() {
        viewModelScope.launch {
            // show splash screen for 2 seconds
            delay(1500)
            _splashLoaded.value = true
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            // set status to loggedIn after 2 seconds
            delay(2000)
            _isLoggedin.value = true
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            delay(2000)
            _isLoggedOut.value = true
        }
    }
    fun resetLogoutUser() {
        _isLoggedOut.value = false
    }

    fun registerUser() {
        viewModelScope.launch {
            // set status to loggedIn after 2 seconds
            delay(2000)
            _isRegistered.value = true
        }
    }

    fun registerSubject() {
        viewModelScope.launch {
            // set status to loggedIn after 2 seconds
            delay(2000)
            _isRegistered.value = true
        }
    }

    fun resetSubjectRegistered() {
        _subjectRegistered.value = false
    }

    fun resetSplashLoaded() {
        _splashLoaded.value = false
    }
}