package com.example.newsappmvvm.data.storage

import com.example.newsappmvvm.utils.UiMode
import kotlinx.coroutines.flow.Flow


interface DataStorage {
    val uiModeFlow: Flow<String>
    suspend fun setUIMode(uiMode: UiMode)
}