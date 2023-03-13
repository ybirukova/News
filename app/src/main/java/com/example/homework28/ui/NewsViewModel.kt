package com.example.homework28.ui

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework28.data.NewsListRepositoryImpl
import com.example.homework28.domain.models.NewsData
import com.example.homework28.ui.utils.NetworkConnection
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsListRepository: NewsListRepositoryImpl,
    private val networkConnection: NetworkConnection,
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

    fun getNewsList() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            delay(2000)
            val list = newsListRepository.getNewsList(networkConnection.isNetworkAvailable())
            _newsLiveData.value = list
            _loadingLiveData.value = false
        }
    }

    fun getNetworkConnectionState(): Boolean {
        return networkConnection.isNetworkAvailable()
    }

    fun onClick(url: String) {
        viewModelScope.launch {
            if (networkConnection.isNetworkAvailable()) {
                _openNewsLiveData.value = url
            } else _openNewsLiveData.value = ""
        }

    }
}