package com.example.newsappmvvm.data.repository

import androidx.paging.*
import com.example.newsappmvvm.data.db.local.AppDB
import com.example.newsappmvvm.data.mapper.MapperArticleImpl
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
        val pagingSourceFactory = { db.getRemoteArticleDao().fetchArticle() }
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

    override suspend fun insertLocalArticle(article: Article): Boolean {
        val localArticle = MapperArticleImpl().map(article)
        return db.getLocalArticleDao().insertLocalArticle(localArticle) > -1
    }

    override suspend fun clearOneItem(localArticle: LocalArticle, reInsertItem: Boolean) {
        if (reInsertItem)
            db.getLocalArticleDao().insertLocalArticle(localArticle)
        else
            db.getLocalArticleDao().clearOneItem(localArticle)
    }

    override suspend fun clearLocalArticles() = db.getLocalArticleDao().clearLocalArticles()

    override fun fetchLocalArticles() = db.getLocalArticleDao().fetchLocalArticles()

    override fun isExistsItem(url: String) = db.getLocalArticleDao().isExistsItem(url)

}