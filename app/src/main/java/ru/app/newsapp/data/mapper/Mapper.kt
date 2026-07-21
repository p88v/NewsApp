package ru.app.newsapp.data.mapper

import ru.app.newsapp.data.local.entity.NewsArticleEntity
import ru.app.newsapp.data.remote.dto.ArticleDto
import ru.app.newsapp.domain.model.NewsArticle


fun NewsArticleEntity.toModel(): NewsArticle {
    return NewsArticle(
        title = this.title,
        content = this.content,
        imageUrl = this.imageUrl,
        source = this.source,
        publishedAt = this.publishedAt,
        articleUrl = this.articleUrl,
        isFavorite = this.isFavorite,
    )
}
fun NewsArticleEntity.fromModel(): NewsArticleEntity {
    return NewsArticleEntity(
        title = this.title,
        content = this.content,
        imageUrl = this.imageUrl,
        source = this.source,
        publishedAt = this.publishedAt,
        articleUrl = this.articleUrl,
        isFavorite = this.isFavorite,
    )
}

fun ArticleDto.toEntity(): NewsArticleEntity{
    return NewsArticleEntity(
        articleUrl = this.url,
        title = this.title.orEmpty(),
        content = this.content.orEmpty(),
        imageUrl = this.urlToImage,
        source = this.source.name.orEmpty(),
        publishedAt = this.publishedAt,
    )
}