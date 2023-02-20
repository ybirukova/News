package com.example.homework28.data

import com.example.homework28.data.mappers.NewsMapper
import com.example.homework28.data.network.NewsService
import com.example.homework28.domain.models.NewsData
import com.example.homework28.domain.repository.NewsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListRepositoryImpl @Inject constructor(
    private val mapper: NewsMapper,
    private val service: NewsService
) : NewsListRepository {

    override suspend fun getNewsList(): List<NewsData> {
        return withContext(Dispatchers.IO) {
            service.getNewsList().execute().body()?.articles?.map { mapper(it) }
                ?: throw Exception()
        }
    }
}