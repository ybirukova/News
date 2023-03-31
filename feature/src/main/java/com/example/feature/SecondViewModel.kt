package com.example.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import com.example.ui_kit.NetworkConnection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<List<NewsData>>()
    val newsLiveData: LiveData<List<NewsData>> get() = _newsLiveData

    private val _searchTextLiveData = MutableLiveData<String>()
    val searchTextLiveData: LiveData<String> get() = _searchTextLiveData

    private val composite = CompositeDisposable()

    fun observeNewsBySearching(str: String) {
        val disposable = str.let { searchText ->
            newsListRepository.getNewsListBySearching(
                networkConnection.isNetworkAvailable(),
                searchText
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _newsLiveData.value = it }, { throwable ->
                })
        }
        composite.add(disposable)
    }

    fun setSearchText (str: String){
        _searchTextLiveData.value = str
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}