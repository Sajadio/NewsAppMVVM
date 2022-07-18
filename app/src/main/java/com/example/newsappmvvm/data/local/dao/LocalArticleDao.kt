package com.example.newsappmvvm.data.local.dao

import androidx.room.*
import com.example.newsappmvvm.data.model.LocalArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: LocalArticle): Long

    @Query("SELECT * FROM LOCAL_ARTICLE_TABLE")
    fun fetchSavedArticles(): Flow<List<LocalArticle>>

    @Query("SELECT EXISTS (SELECT 1 FROM LOCAL_ARTICLE_TABLE WHERE url = :url)")
    fun existsItem(url: String): Flow<Boolean>

    @Delete
    suspend fun deleteOneItem(article: LocalArticle)

    @Query("DELETE FROM LOCAL_ARTICLE_TABLE")
    suspend fun deleteAllItem()

}