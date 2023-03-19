package com.example.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import com.example.ui_kit.NetworkConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val _newsLiveDataBySearching = MutableLiveData<List<NewsData>?>()
    val newsLiveDataBySearching: MutableLiveData<List<NewsData>?> get() = _newsLiveDataBySearching

    private val _searchLiveData = MutableLiveData<String>()
    val searchLiveData: MutableLiveData<String> get() = _searchLiveData

    fun getNewsListBySearching(q: String) {
        viewModelScope.launch {
            delay(3000)
            val list =
                newsListRepository.getNewsListBySearching(networkConnection.isNetworkAvailable(), q)
            _newsLiveDataBySearching.value = list
        }
    }

    fun setSearchWord(str: String) {
        _searchLiveData.value = str
    }
}