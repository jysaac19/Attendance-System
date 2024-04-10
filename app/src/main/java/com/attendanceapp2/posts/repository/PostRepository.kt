package com.attendanceapp2.posts.repository

import com.attendanceapp2.posts.model.Post

interface PostRepository {

    // Retrieve all posts
    suspend fun getPosts(): List<Post>
}