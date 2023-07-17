package com.example.newz.db

data class news(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)