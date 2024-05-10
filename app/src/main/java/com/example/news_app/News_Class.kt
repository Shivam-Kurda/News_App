package com.example.news_app

data class News_Class(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)