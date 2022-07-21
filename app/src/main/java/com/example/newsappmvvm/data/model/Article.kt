package com.example.newsappmvvm.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.newsappmvvm.utils.ARTICLE_TABLE
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = ARTICLE_TABLE, indices = [Index(value = ["urlToImage"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int = 0,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
):Serializable