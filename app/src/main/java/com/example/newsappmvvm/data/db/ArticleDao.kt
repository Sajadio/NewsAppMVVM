package com.example.newsappmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappmvvm.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM tb_article")
    fun getSavedArticle(): LiveData<List<Article>>

    @Query("SELECT EXISTS (SELECT 1 FROM tb_article WHERE url = :url)")
    fun existsItem(url: String): LiveData<Boolean>

    @Delete
    suspend fun deleteOneItem(article: Article)

    @Query("DELETE FROM tb_article")
    suspend fun deleteAllItem()
}