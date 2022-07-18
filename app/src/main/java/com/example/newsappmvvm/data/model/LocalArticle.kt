package com.example.newsappmvvm.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.newsappmvvm.utils.LOCAL_ARTICLE_TABLE
import java.io.Serializable

@Entity(tableName = LOCAL_ARTICLE_TABLE, indices = [Index(value = ["urlToImage"], unique = true)])
data class LocalArticle(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int = 0,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val url: String? = null,
    val source: Source? = null,
    val urlToImage: String? = null,
) : Serializable