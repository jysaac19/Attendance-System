package com.attendanceapp2.data.model

sealed class Results {
    data class AddSubjectResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class AddUserResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class JoinSubjectResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    )

    data class UpdateUserResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class UpdateSubjectResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()
}
