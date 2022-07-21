package com.example.newsappmvvm.data.repository

import androidx.paging.PagingData
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getBreakingNews(): Flow<PagingData<Article>>
    fun getResponseDataByQuery(query: String): Flow<PagingData<Article>>
    fun getResponseDataByCategory(category: String): Flow<PagingData<Article>>
    suspend fun insertLocalArticle(article: Article): Boolean
    suspend fun clearOneItem(localArticle: LocalArticle, reInsertItem: Boolean)
    suspend fun clearLocalArticles()
    fun fetchLocalArticles(): Flow<List<LocalArticle>>
    fun isExistsItem(url: String): Flow<Boolean>

}