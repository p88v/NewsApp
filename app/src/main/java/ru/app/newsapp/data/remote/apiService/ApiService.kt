package ru.app.newsapp.data.remote.apiService

import retrofit2.http.GET
import retrofit2.http.Query
import ru.app.newsapp.data.remote.dto.NewsResponseDto

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "ru",
        @Query("apiKey") apiKey: String
    ): NewsResponseDto
}