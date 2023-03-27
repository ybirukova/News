package com.example.homework28.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import com.example.ui_kit.NetworkConnection
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<List<NewsData>>()
    val newsLiveData: LiveData<List<NewsData>> get() = _newsLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val _openNewsLiveData = MutableLiveData<String>()
    val openNewsLiveData: LiveData<String> get() = _openNewsLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadingLiveData.value = false
        when (throwable) {
            else -> {
                _errorLiveData.value = "error"
            }
        }
    }

    init {
        refreshData()
        observeNews()
    }

    private fun refreshData() {
        viewModelScope.launch(exceptionHandler) {
            newsListRepository.getNewsListFromApiToDatabase()
        }
    }

    private fun observeNews() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            delay(4000)
            newsListRepository.getNewsList(networkConnection.isNetworkAvailable()).collect {
                _newsLiveData.value = it
                _loadingLiveData.value = false
            }
        }
    }

    fun onClick(url: String) {
        viewModelScope.launch {
            if (networkConnection.isNetworkAvailable()) {
                _openNewsLiveData.value = url
            } else _openNewsLiveData.value = ""
        }
    }
}