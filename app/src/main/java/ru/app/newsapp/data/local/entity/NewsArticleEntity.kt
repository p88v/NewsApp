package ru.app.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsArticleEntity(

    @PrimaryKey
    val articleUrl: String,

    val title: String,
    val content: String,
    val imageUrl: String?,
    val source: String,
    val publishedAt: String,
    val isFavorite: Boolean = false,
)
