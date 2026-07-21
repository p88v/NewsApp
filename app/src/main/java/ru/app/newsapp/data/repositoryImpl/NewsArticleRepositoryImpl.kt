package ru.app.newsapp.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.app.newsapp.BuildConfig
import ru.app.newsapp.data.local.dao.Dao
import ru.app.newsapp.data.mapper.toEntity
import ru.app.newsapp.data.mapper.toModel
import ru.app.newsapp.data.remote.apiProvider.ApiProvider
import ru.app.newsapp.domain.model.NewsArticle
import ru.app.newsapp.domain.repository.NewsArticleRepository

class NewsArticleRepositoryImpl(private val dao: Dao) : NewsArticleRepository {

    override fun getAll(): Flow<List<NewsArticle>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                entity.toModel()
            }
        }
    }


    override suspend fun refresh() {
        val response = ApiProvider.apiService.getNews(
            country = "us",
            apiKey = BuildConfig.NEWS_API_KEY
        )

        val entities = response.articles.map { articleDto ->
            articleDto.toEntity()
        }

        dao.insert(entities)
    }

    override suspend fun toFavorite(articleId: String, isFavotite: Boolean) {
        dao.addFavorite(articleId, isFavotite)
    }
}