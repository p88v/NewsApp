package ru.app.newsapp.domain.model

data class NewsArticle(
    val title: String,
    val content:String,
    val imageUrl: String?,
    val source: String,
    val publishedAt: String,
    val articleUrl: String,
    val isFavorite: Boolean = false,
)
