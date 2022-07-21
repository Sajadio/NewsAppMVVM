package com.example.newsappmvvm.data.network

import com.example.newsappmvvm.data.model.News
import com.example.newsappmvvm.utils.API_KEY
import com.example.newsappmvvm.utils.language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String = language(),
        @Query("page") pageNumber: Int?,
        @Query("pageSize") pageSize:Int?,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<News>

    @GET("v2/top-headlines")
    suspend fun getResponseDataByCategory(
        @Query("country") country: String = language(),
        @Query("category") category: String,
        @Query("page") pageNumber: Int?,
        @Query("pageSize") pageSize:Int?,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<News>

    @GET("v2/everything")
    suspend fun getResponseDataByQuery(
        @Query("country") country: String = language(),
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int?,
        @Query("pageSize") pageSize:Int?,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<News>
}