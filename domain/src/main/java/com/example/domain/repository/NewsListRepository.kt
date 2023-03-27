package com.example.domain.repository

import com.example.domain.models.NewsData
import kotlinx.coroutines.flow.Flow

interface NewsListRepository {

    suspend fun getNewsListFromApiToDatabase(): List<NewsData>

    suspend fun getNewsList(isNetworkConnection: Boolean): Flow<List<NewsData>>

    suspend fun getNewsListBySearching(
        isNetworkConnection: Boolean,
        q: String
    ): Flow<List<NewsData>>
}