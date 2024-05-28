package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.model.subject.Subject


interface SubjectRepository {

    suspend fun insertSubject(subject : Subject)

    suspend fun updateSubject(subject : Subject)

    suspend fun deleteSubject(subject : Subject)

    suspend fun getSubjectsByIds(subjectIds: List<Int>): List<Subject>
}