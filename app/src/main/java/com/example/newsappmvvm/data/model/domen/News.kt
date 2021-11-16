package com.example.newsappmvvm.data.model.domen

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    val articles: List<Article>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
)