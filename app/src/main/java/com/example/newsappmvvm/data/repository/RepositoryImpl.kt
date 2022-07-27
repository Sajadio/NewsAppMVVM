package com.example.newsappmvvm.data.repository

import androidx.paging.*
import com.example.newsappmvvm.data.db.local.AppDB
import com.example.newsappmvvm.data.mapper.MapperRemoteArticleImpl
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.model.LocalArticle
import com.example.newsappmvvm.data.network.ApiService
import com.example.newsappmvvm.data.paging.datasource.ExplorePagingSource
import com.example.newsappmvvm.data.paging.remotemediator.ArticleRemoteMediator
import com.example.newsappmvvm.data.paging.datasource.SearchPagingSource
import com.example.newsappmvvm.utils.ITEMS_PER_PAGE
import com.example.newsappmvvm.utils.PREF_DISTANCE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class RepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: AppDB,
) : Repository {


    override fun getBreakingNews(): Flow<PagingData<Article>> {
        val pagingSourceFactory = { db.getRemoteArticleDao().fetchArticles() }
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


    override fun getResponseDataByQuery(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREF_DISTANCE,
                enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSource(api = api, query = query) }
        ).flow
    }

    override fun getResponseDataByCategory(category: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREF_DISTANCE,
                enablePlaceholders = true),
            pagingSourceFactory = { ExplorePagingSource(api = api, category = category) }
        ).flow
    }

    override suspend fun insertLocalArticles(article: Article): Boolean {
        val localArticle = MapperRemoteArticleImpl().map(article)
        return db.getLocalArticleDao().insertLocalArticles(localArticle) > -1
    }

    override suspend fun clearLocalArticle(localArticle: LocalArticle, reInsertItem: Boolean) {
        if (reInsertItem)
            db.getLocalArticleDao().insertLocalArticles(localArticle)
        else
            db.getLocalArticleDao().clearLocalArticle(localArticle)
    }

    override suspend fun clearAllLocalArticles() = db.getLocalArticleDao().clearAllLocalArticles()

    override fun fetchLocalArticles() = db.getLocalArticleDao().fetchLocalArticles()

   override fun checkExistsItem(url: String) = db.getLocalArticleDao().checkExistsItem(url)

}