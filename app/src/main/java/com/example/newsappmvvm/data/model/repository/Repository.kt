package com.example.newsappmvvm.data.model.repository

import com.example.newsappmvvm.data.model.db.ArticleDatabase
import com.example.newsappmvvm.data.model.domen.Article
import com.example.newsappmvvm.data.model.network.ApiProvider
import com.example.newsappmvvm.utils.SafeApiCall

class Repository(private val db: ArticleDatabase) : SafeApiCall {

    suspend fun getBreakingNews(category: String) =
        safeApiCall { ApiProvider.api.getBreakingNews(category = category) }

    suspend fun getSearchingQuery(query: String) =
        safeApiCall { ApiProvider.api.getSearchingQuery(query) }

    suspend fun insert(article: Article) {
        db.getDao().insert(article)
    }

    suspend fun deleteOneItem(article: Article) = db.getDao().deleteOneItem(article)
    suspend fun deleteAllItem() = db.getDao().deleteAllItem()

    fun getSavedArticle() = db.getDao().getSavedArticle()

    fun existsItem(url: String) = db.getDao().existsItem(url)

}