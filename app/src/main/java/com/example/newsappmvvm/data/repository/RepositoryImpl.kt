package com.example.newsappmvvm.data.repository

import android.util.Log
import androidx.paging.*
import com.example.newsappmvvm.data.local.AppDB
import com.example.newsappmvvm.data.mapper.MapperArticleImpl
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.data.network.ApiService
import com.example.newsappmvvm.data.paging.datasource.ExplorePagingSource
import com.example.newsappmvvm.data.paging.remotemediator.ArticleRemoteMediator
import com.example.newsappmvvm.data.paging.datasource.SearchPagingSource
import com.example.newsappmvvm.utils.ITEMS_PER_PAGE
import com.example.newsappmvvm.utils.PREF_DISTANCE
import com.example.newsappmvvm.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RepositoryImpl(
    private val api: ApiService,
    private val db: AppDB,
) : SafeApiCall {


    fun getBreakingNews(): Flow<PagingData<Article>> {
        val pagingSourceFactory = { db.getRemoteArticleDao().fetchAllItem() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREF_DISTANCE,
                enablePlaceholders = true),
            remoteMediator = ArticleRemoteMediator(
                api = api,
                db = db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    fun getResponseDataByQuery(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREF_DISTANCE,
                enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSource(api = api, query = query) }
        ).flow
    }

    fun getResponseDataByCategory(category: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREF_DISTANCE,
                enablePlaceholders = true),
            pagingSourceFactory = { ExplorePagingSource(api = api, category = category) }
        ).flow
    }

    suspend fun insert(article: Article) {
        val localArticle = MapperArticleImpl().map(article)
        db.getLocalArticleDao().insert(localArticle)
    }

    suspend fun deleteOneItem(localArticle: LocalArticle, reInsertItem: Boolean) {
        if (reInsertItem)
            db.getLocalArticleDao().insert(localArticle)
        else
            db.getLocalArticleDao().deleteOneItem(localArticle)
    }

    suspend fun deleteAllItem() = db.getLocalArticleDao().deleteAllItem()

    fun fetchSavedArticles() = db.getLocalArticleDao().fetchSavedArticles()

    fun existsItem(url: String) = db.getLocalArticleDao().existsItem(url)

}