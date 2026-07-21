package ru.app.newsapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.app.newsapp.domain.model.NewsArticle

interface NewsArticleRepository {

    fun getAll(): Flow<List<NewsArticle>>

    suspend fun refresh()

    suspend fun toFavorite(articleId: String, isFavotite: Boolean)
}
