package com.example.newsappmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappmvvm.data.local.convert.Convert
import com.example.newsappmvvm.data.local.dao.ArticleDao
import com.example.newsappmvvm.data.local.dao.LocalArticleDao
import com.example.newsappmvvm.data.local.dao.PageKeysDao
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.data.model.PageKeys
import com.example.newsappmvvm.utils.NAME_DATABASE

@Database(entities = [Article::class, PageKeys::class,LocalArticle::class], version = 1, exportSchema = false)
@TypeConverters(Convert::class)
abstract class AppDB : RoomDatabase() {

    abstract fun getRemoteArticleDao(): ArticleDao
    abstract fun getLocalArticleDao(): LocalArticleDao
    abstract fun getPageKeysDao(): PageKeysDao

    companion object {
        @Volatile
        private var instance: AppDB? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).apply { instance }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                NAME_DATABASE
             ).build()
    }
}