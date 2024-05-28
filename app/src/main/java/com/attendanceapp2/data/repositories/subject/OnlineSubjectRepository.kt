package com.attendanceapp2.data.repositories.subject

import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.network.HttpRoutes
import com.professorsattendanceapp.network.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import timber.log.Timber

class OnlineSubjectRepository(private val ktorClient: HttpClient = KtorClient()) {
    suspend fun getAllSubjects(): List<Subject> {
        return try {
            val response: List<Subject> = ktorClient.request(HttpRoutes.SUBJECTS) {
                method = HttpMethod.Get
                url(HttpRoutes.SUBJECTS)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get all subjects")
            emptyList()
        }
    }

    suspend fun addSubject(subject: Subject): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.ADD_SUBJECT) {
                method = HttpMethod.Post
                url(HttpRoutes.ADD_SUBJECT)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(subject)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to add subject")
            "Error adding subject"
        }
    }

    suspend fun updateSubject(subject: Subject): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.UPDATE_SUBJECT) {
                method = HttpMethod.Put
                url(HttpRoutes.UPDATE_SUBJECT)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(subject)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to update subject")
            "Error updating subject"
        }
    }

    suspend fun deleteSubject(subjectId: Int): String {
        return try {
            val response: String = ktorClient.request("${HttpRoutes.DELETE_SUBJECT}/$subjectId") {
                method = HttpMethod.Delete
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete subject")
            "Error deleting subject"
        }
    }

    suspend fun getSubjectsByIds(subjectIds: String): List<Subject> {
        return try {
            val response: List<Subject> = ktorClient.request("${HttpRoutes.GET_SUBJECT_BY_IDS}?id=${subjectIds}") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Failed to get subjects by IDs")
            emptyList()
        }
    }

    suspend fun getSubjectByCode(subjectCode: String): Subject? {
        return try {
            ktorClient.request("${HttpRoutes.GET_SUBJECT_BY_CODE}/$subjectCode") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            Timber.e(e, "Failed to get subject by code")
            null
        }
    }

    suspend fun getSubjectByName(subjectName: String): Subject? {
        return try {
            ktorClient.request("${HttpRoutes.GET_SUBJECT_BY_NAME}/$subjectName") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            Timber.e(e, "Failed to get subject by name")
            null
        }
    }

    suspend fun getSubjectByJoinCode(joinCode: String): Subject? {
        return try {
            ktorClient.request("${HttpRoutes.GET_SUBJECT_BY_JOIN_CODE}/$joinCode") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            Timber.e(e, "Failed to get subject by join code")
            null
        }
    }
}