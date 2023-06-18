package com.example.homework28.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import com.example.ui_kit.NetworkConnection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
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

    private val composite = CompositeDisposable()

    init {
        refreshData()
        observeNews()
    }

    private fun refreshData() {
        val disposable = newsListRepository.getNewsListFromApiToDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        composite.add(disposable)
    }

    private fun observeNews() {
        _loadingLiveData.value = true

        val disposable = newsListRepository.getNewsList(networkConnection.isNetworkAvailable())
            .subscribeOn(Schedulers.io())
            .delay(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe({
                _newsLiveData.value = it
                _loadingLiveData.value = false
            }, { throwable ->
            })

        composite.add(disposable)
    }

    fun onClick(url: String) {
        viewModelScope.launch {
            if (networkConnection.isNetworkAvailable()) {
                _openNewsLiveData.value = url
            } else _openNewsLiveData.value = ""
        }
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}