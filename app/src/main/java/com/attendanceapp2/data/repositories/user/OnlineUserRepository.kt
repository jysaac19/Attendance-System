package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.model.user.User
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

class OnlineUserRepository(private val ktorClient: HttpClient = KtorClient()) {
    suspend fun getAllUsers(): List<User> {
        val response: List<User> = ktorClient.request(HttpRoutes.USERS) {
            method = HttpMethod.Get
            url(HttpRoutes.USERS)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        response.forEach {
            Timber.i("$it")
        }

        return response
    }

    suspend fun addUser(user: User): String {
        val response: String = ktorClient.request(HttpRoutes.ADDUSER) {
            method = HttpMethod.Post
            url(HttpRoutes.ADDUSER)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(user)
        }.body()
        response.forEach {
            Timber.i("$it")
        }

        return response
    }

    suspend fun updateUser(user: User): String {
        val response: String = ktorClient.request(HttpRoutes.UPDATE_USER) {
            method = HttpMethod.Put
            url(HttpRoutes.UPDATE_USER)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(user)
        }.body()
        response.forEach {
            Timber.i("$it")
        }

        return response
    }

    suspend fun deleteUser(userId: Int): String {
        val response: String = ktorClient.request("${HttpRoutes.DELETE_USER}/$userId") {
            method = HttpMethod.Delete
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        response.forEach {
            Timber.i("$it")
        }

        return response
    }

    suspend fun getUserByEmailPassword(email: String, password: String): User? {
        val response: User? = ktorClient.request("${HttpRoutes.GET_USER_BY_EMAIL_PASSWORD}/$email/$password") {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        Timber.i("response $response")

        return response
    }

    suspend fun getUserByEmail(email: String): User? {
        val response: User? = ktorClient.request("${HttpRoutes.GET_USER_BY_EMAIL_PASSWORD}?email=$email") {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        Timber.i("response $response")

        return response
    }

    suspend fun getUserByFullName(firstname: String, lastname: String): User? {
        val response: User? = ktorClient.request("${HttpRoutes.GET_USER_BY_FULL_NAME}/$firstname/$lastname") {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        Timber.i("response $response")

        return response
    }
}