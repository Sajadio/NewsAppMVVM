package com.example.newsappmvvm.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappmvvm.model.domen.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM tb_article")
    fun fetchArticles(): LiveData<List<Article>>

    @Query("SELECT EXISTS (SELECT 1 FROM tb_article WHERE url = :url)")
    fun existsItem(url: String): LiveData<Boolean>

    @Delete
    suspend fun deleteOneItem(article: Article)

    @Query("DELETE FROM tb_article")
    suspend fun deleteAllItem()
}