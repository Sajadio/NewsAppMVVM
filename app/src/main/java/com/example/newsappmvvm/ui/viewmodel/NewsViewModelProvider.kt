package com.example.newsappmvvm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.data.repository.RepositoryImpl
import com.example.newsappmvvm.data.storage.DataStorageImp
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@ExperimentalPagingApi
class NewsViewModelFactory @Inject constructor(
    private val repositoryImpl: RepositoryImpl,
    private val dataStorage: DataStorageImp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repositoryImpl,dataStorage) as T
    }
}