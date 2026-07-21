package ru.app.newsapp.data.remote.apiProvider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.app.newsapp.data.remote.apiService.ApiService

object ApiProvider {


    private val BASE_URL = "https://newsapi.org/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}