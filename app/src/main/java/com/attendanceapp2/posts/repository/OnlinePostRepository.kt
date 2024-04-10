package com.attendanceapp2.posts.repository

import com.attendanceapp2.posts.model.Post
import com.vpmobiledev.pocketswiss.network.HttpRoutes
import com.professorsattendanceapp.network.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import timber.log.Timber

class OnlinePostRepository (private val ktorClient: HttpClient = KtorClient()): PostRepository {
    override suspend fun getPosts(): List<Post> {
        val response: List<Post> = ktorClient.request(HttpRoutes.POSTS) {
            method = HttpMethod.Get
            url(HttpRoutes.POSTS)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        Timber.i("response $response")

        return response
    }
}