package com.example.newsappmvvm.data.paging.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsappmvvm.data.local.AppDB
import com.example.newsappmvvm.data.model.PageKeys
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.network.ApiService
import com.example.newsappmvvm.utils.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class ArticleRemoteMediator(
    private val api: ApiService,
    private val db: AppDB,
) : RemoteMediator<Int, Article>() {

    private val articleDao = db.getRemoteArticleDao()
    private val pageKeysDao = db.getPageKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>,
    ): MediatorResult {
        val currentPage = when (loadType) {

            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: DEFAULT_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val response = api.getBreakingNews(pageNumber = currentPage,
                pageSize = state.config.pageSize).body()?.articles
            val endOfPaginationReached = response?.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDao.deleteAllItem()
                    pageKeysDao.deleteAllRemoteKeys()
                }

                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached == true) null else currentPage + 1

                val keys = response?.map { article ->
                    PageKeys(
                        id = article.articleId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys?.let { pageKeysDao.insertAllPageKeys(remoteKeys = keys) }
                response?.let { articleDao.insertItems(article = response) }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): PageKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                pageKeysDao.fetchRemoteKeys(article.articleId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>,
    ): PageKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.articleId?.let { id ->
                pageKeysDao.fetchRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): PageKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                pageKeysDao.fetchRemoteKeys(article.articleId)
            }
    }
}