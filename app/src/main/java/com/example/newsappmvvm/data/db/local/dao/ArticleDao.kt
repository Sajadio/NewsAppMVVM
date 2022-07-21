package com.example.newsappmvvm.data.db.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsappmvvm.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: List<Article>)

    @Query("SELECT * FROM ARTICLE_TABLE ORDER BY publishedAt DESC")
    fun fetchArticle(): PagingSource<Int, Article>

    @Query("DELETE FROM ARTICLE_TABLE")
    suspend fun clearAllArticles()

}