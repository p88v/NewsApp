package ru.app.newsapp.data.local.appDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.app.newsapp.data.local.dao.Dao
import ru.app.newsapp.data.local.entity.NewsArticleEntity

@Database(entities = [NewsArticleEntity::class], version = 1 )
abstract class AppDb: RoomDatabase(){

    abstract fun getDao(): Dao


    companion object{

        @Volatile
        private var instace: AppDb? = null


        fun getInstance(context: Context): AppDb{
            return instace?: synchronized(this){
                instace?: dataBaseBuilder(context.applicationContext).also {
                    instace = it
                }
            }
        }

        fun dataBaseBuilder(context: Context): AppDb{
            return Room.databaseBuilder(
                context.applicationContext,
                AppDb::class.java,
                "app.db"
            ).build()
        }

    }
}