package com.example.newsappmvvm.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsappmvvm.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(article: List<Article>)

    @Query("SELECT * FROM ARTICLE_TABLE ORDER BY publishedAt DESC")
    fun fetchAllItem(): PagingSource<Int, Article>

    @Query("DELETE FROM ARTICLE_TABLE")
    suspend fun deleteAllItem()

}