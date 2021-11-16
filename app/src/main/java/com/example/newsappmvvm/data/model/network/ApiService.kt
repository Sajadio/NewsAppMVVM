package com.example.newsappmvvm.data.model.network

import com.example.newsappmvvm.data.model.domen.News
import com.example.newsappmvvm.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String = "us",
        @Query("category")
        category: String? = null,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): News

    @GET("v2/everything")
    suspend fun getSearchingQuery(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): News
}