package com.attendanceapp2.data.model

sealed class Results {
    data class AddSubjectResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()
}
