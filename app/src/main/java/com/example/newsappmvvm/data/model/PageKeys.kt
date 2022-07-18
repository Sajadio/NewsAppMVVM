package com.example.newsappmvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsappmvvm.utils.PAGE_KEY_TABLE

@Entity(tableName = PAGE_KEY_TABLE)
data class PageKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)