package com.attendanceapp2.data.model.subject

import androidx.room.Entity

@Entity(tableName = "UserSubjectCrossRef", primaryKeys = ["userId", "subjectId"])
data class UserSubjectCrossRef(
    val userId: Long,
    val subjectId: Long
)
