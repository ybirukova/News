package com.example.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import com.example.ui_kit.NetworkConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val trigger = MutableStateFlow("")

    fun setQuery(query: String) {
        trigger.value = query
    }

    suspend fun getResult(): Flow<List<NewsData>> {
        while (true) {
            delay(2000)
            if (trigger.value.isNotBlank()) {
                return trigger.flatMapLatest { query ->
                    newsListRepository.getNewsListBySearching(
                        networkConnection.isNetworkAvailable(),
                        query
                    )
                        .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(5000L),
                            initialValue = emptyList()
                        )
                }
            } else delay(1000L)
        }
    }
}