package com.example.newsappmvvm.data.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappmvvm.data.db.local.convert.Convert
import com.example.newsappmvvm.data.db.local.dao.ArticleDao
import com.example.newsappmvvm.data.db.local.dao.LocalArticleDao
import com.example.newsappmvvm.data.db.local.dao.PageKeysDao
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.data.model.PageKeys

@Database(entities = [Article::class, PageKeys::class, LocalArticle::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Convert::class)
abstract class AppDB : RoomDatabase() {

    abstract fun getRemoteArticleDao(): ArticleDao
    abstract fun getLocalArticleDao(): LocalArticleDao
    abstract fun getPageKeysDao(): PageKeysDao

}