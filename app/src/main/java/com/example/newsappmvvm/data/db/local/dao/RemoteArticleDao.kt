package com.example.newsappmvvm.data.db.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsappmvvm.data.model.Article

@Dao
interface RemoteArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<Article>)

    @Query("SELECT * FROM ARTICLE_TABLE ORDER BY publishedAt DESC")
    fun fetchArticles(): PagingSource<Int, Article>

    @Query("DELETE FROM ARTICLE_TABLE")
    suspend fun clearAllRemoteArticles()

}