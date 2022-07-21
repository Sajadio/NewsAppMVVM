package com.example.newsappmvvm.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappmvvm.utils.Event
import com.example.newsappmvvm.utils.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    var errorMessage = MutableLiveData<Event<String?>>()

    inline fun <T> collectResponse(
        crossinline execute: suspend () -> Flow<NetworkStatus<T?>>,
        crossinline onSuccess: (Flow<NetworkStatus<T?>>) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                errorMessage.postValue(Event(ex.message))
            }
        }
    }

}