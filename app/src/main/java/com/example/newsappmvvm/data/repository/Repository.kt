package com.example.newsappmvvm.data.repository

import androidx.paging.PagingData
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insertLocalArticles(article: Article): Boolean
    suspend fun clearLocalArticle(localArticle: LocalArticle, reInsertItem: Boolean)
    suspend fun clearAllLocalArticles()

    fun getBreakingNews(): Flow<PagingData<Article>>
    fun getResponseDataByQuery(query: String): Flow<PagingData<Article>>
    fun getResponseDataByCategory(category: String): Flow<PagingData<Article>>
    fun fetchLocalArticles(): Flow<List<LocalArticle>>
    fun checkExistsItem(url: String): Flow<Boolean>

}