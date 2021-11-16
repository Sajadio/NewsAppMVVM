package com.example.newsappmvvm.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappmvvm.data.model.repository.Repository

class NewsViewModelProvider(
    private val repository: Repository
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}