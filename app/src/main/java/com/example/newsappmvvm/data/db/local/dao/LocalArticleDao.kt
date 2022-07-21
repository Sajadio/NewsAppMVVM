package com.example.newsappmvvm.data.db.local.dao

import androidx.room.*
import com.example.newsappmvvm.data.model.LocalArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalArticle(localArticle: LocalArticle): Long

    @Query("SELECT * FROM LOCAL_ARTICLE_TABLE")
    fun fetchLocalArticles(): Flow<List<LocalArticle>>

    @Query("SELECT EXISTS (SELECT 1 FROM LOCAL_ARTICLE_TABLE WHERE url = :url)")
    fun isExistsItem(url: String): Flow<Boolean>

    @Delete
    suspend fun clearArticle(localArticle: LocalArticle)

    @Query("DELETE FROM LOCAL_ARTICLE_TABLE")
    suspend fun clearLocalArticles()

}