package com.attendanceapp2.data.model.subject

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "UserSubjectCrossRef", primaryKeys = ["userId", "subjectId"])
data class UserSubjectCrossRef(
    val userId: Int,
    val subjectId: Int
)