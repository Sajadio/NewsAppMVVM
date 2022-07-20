package com.example.newsappmvvm.data.repository

import androidx.paging.PagingData
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getBreakingNews(): Flow<PagingData<Article>>
    fun getResponseDataByQuery(query: String): Flow<PagingData<Article>>
    fun getResponseDataByCategory(category: String): Flow<PagingData<Article>>
    suspend fun insert(article: Article)
    suspend fun deleteOneItem(localArticle: LocalArticle, reInsertItem: Boolean)
    suspend fun deleteAllItem()
    fun fetchSavedArticles(): Flow<List<LocalArticle>>
    fun existsItem(url: String): Flow<Boolean>

}