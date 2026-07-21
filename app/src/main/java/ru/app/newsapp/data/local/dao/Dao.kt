package ru.app.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.app.newsapp.data.local.entity.NewsArticleEntity


@Dao
interface Dao {


    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleEntityList: List<NewsArticleEntity>)


    @Query("""
        UPDATE news
        SET isFavorite =:isFavorite
        WHERE articleUrl =:articleUrl
    """)
    suspend fun addFavorite(
        articleUrl: String,
        isFavorite: Boolean
    )



    @Query("DELETE FROM news WHERE articleUrl = :articleUrl")
    suspend fun remove(articleUrl: String)

}