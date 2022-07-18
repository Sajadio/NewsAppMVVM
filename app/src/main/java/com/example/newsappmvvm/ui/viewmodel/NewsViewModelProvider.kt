package com.example.newsappmvvm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.data.repository.RepositoryImpl

@Suppress("UNCHECKED_CAST")
@ExperimentalPagingApi
class NewsViewModelProvider(
    private val repository: RepositoryImpl
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}