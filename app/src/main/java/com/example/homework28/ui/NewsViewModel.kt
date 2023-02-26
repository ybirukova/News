package com.example.homework28.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework28.data.NewsListRepositoryImpl
import com.example.homework28.domain.models.NewsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsListRepository: NewsListRepositoryImpl,
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<List<NewsData>>()
    val newsLiveData: LiveData<List<NewsData>> get() = _newsLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

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
            val list = newsListRepository.getNewsList()
            _newsLiveData.value = list
            _loadingLiveData.value = false
        }
    }
}