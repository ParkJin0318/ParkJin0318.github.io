package com.parkjin.blog.model

data class Post(
    val route: String,
    val category: String,
    val title: String,
    val description: String,
    val createdAt: String,
    val tags: List<String>,
)
