package com.attendanceapp2.data.model

import android.content.Context
import android.widget.Toast

sealed class Results {
    data class AddSubjectResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class AddUserResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class AddAttendanceResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class AddScheduleResult(
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

    data class UpdateAttendanceResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class LoginResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()

    data class FilterAttendanceResult(
        val failureMessage: String? = null,
        val successMessage: String? = null
    ) : Results()
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}