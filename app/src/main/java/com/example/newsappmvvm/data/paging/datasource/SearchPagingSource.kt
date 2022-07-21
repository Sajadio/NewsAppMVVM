package com.example.newsappmvvm.data.paging.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappmvvm.data.model.Article
import com.example.newsappmvvm.data.network.ApiService
import com.example.newsappmvvm.utils.DEFAULT_PAGE_INDEX


class SearchPagingSource(
    private val api: ApiService,
    private val query: String,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNumber = params.key ?: DEFAULT_PAGE_INDEX

        return try {
            val response = api.getResponseDataByQuery(searchQuery = query,
                pageNumber = pageNumber,
                pageSize = params.loadSize)
            val data = response.body()?.articles

            LoadResult.Page(
                data = data ?: emptyList(),
                prevKey = if (pageNumber == DEFAULT_PAGE_INDEX) null else pageNumber.minus(1),
                nextKey = if (data?.isEmpty() == true) null else pageNumber.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}