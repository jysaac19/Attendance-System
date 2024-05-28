package com.attendanceapp2.data.repositories.usersubjectcossref

import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
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

class OnlineUserSubjectCrossRefRepository(private val ktorClient: HttpClient = KtorClient()) {

    suspend fun getAllUserSubCrossRef(): List<UserSubjectCrossRef> {
        return try {
            val response: List<UserSubjectCrossRef> = ktorClient.request(HttpRoutes.USER_SUBJECT_CROSS_REF) {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Error fetching all UserSubjectCrossRef")
            emptyList()
        }
    }

    suspend fun addUserSubCrossRef(userSubjectCrossRef: UserSubjectCrossRef): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.ADD_USER_SUBJECT_CROSS_REF) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(userSubjectCrossRef)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Error adding UserSubjectCrossRef")
            "Error: ${e.localizedMessage}"
        }
    }

    suspend fun deleteUserSubCrossRef(userSubjectCrossRef: UserSubjectCrossRef): String {
        return try {
            val response: String = ktorClient.request(HttpRoutes.DELETE_USER_SUBJECT_CROSS_REF) {
                method = HttpMethod.Delete
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(userSubjectCrossRef)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Error deleting UserSubjectCrossRef")
            "Error: ${e.localizedMessage}"
        }
    }

    suspend fun getUserSubCrossRefByUserId(userId: Int): List<UserSubjectCrossRef> {
        return try {
            val response: List<UserSubjectCrossRef> = ktorClient.request("${HttpRoutes.GET_USER_SUBJECT_CROSS_REF_BY_USER_ID}/$userId") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Error fetching UserSubjectCrossRef by userId")
            emptyList()
        }
    }

    suspend fun getUserSubCrossRefByUserIdSubjectId(userId: Int, subjectId: Int): List<UserSubjectCrossRef> {
        return try {
            val response: List<UserSubjectCrossRef> = ktorClient.request("${HttpRoutes.GET_USER_SUBJECT_CROSS_REF_BY_USER_ID_SUBJECT_ID}/$userId/$subjectId") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }.body()
            response.forEach {
                Timber.i("$it")
            }
            response
        } catch (e: Exception) {
            Timber.e(e, "Error fetching UserSubjectCrossRef by userId and subjectId")
            emptyList()
        }
    }
}