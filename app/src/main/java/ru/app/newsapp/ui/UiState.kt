package ru.app.newsapp.ui

import ru.app.newsapp.domain.model.NewsArticle


data class UiState(
    val news:List<NewsArticle> = emptyList(),
    val empty: Boolean = news.isEmpty(),
    val loading: Boolean = false,
    val error: String? = null,
)
