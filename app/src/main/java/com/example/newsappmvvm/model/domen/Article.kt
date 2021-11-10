package com.example.newsappmvvm.model.domen

import android.icu.text.SimpleDateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.newsappmvvm.utils.getCountry
import com.google.gson.annotations.SerializedName
import org.ocpsoft.prettytime.PrettyTime
import java.io.Serializable
import java.util.*

@Entity(tableName = "tb_article", indices = [Index(value = ["url"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int = 0,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @SerializedName("source")
    val source: Source? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("urlToImage")
    val urlToImage: String? = null
) : Serializable {
    fun dateToTimeFormat(): String? {
        val prettyTime = PrettyTime(Locale(getCountry()))
        var isTime: String? = null
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val date: Date = sdf.parse(publishedAt)
        isTime = prettyTime.format(date)
        return isTime
    }

    fun dateFormat(): String? {
        val newDate: String?
        val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(getCountry()))
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(publishedAt)
        newDate = dateFormat.format(date)
        return newDate
    }
}