package com.example.domain.repository

import com.example.domain.models.NewsData

interface NewsListRepository {

    suspend fun getNewsList(isNetworkConnection: Boolean): List<NewsData>

    suspend fun getNewsListBySearching(isNetworkConnection: Boolean, q: String): List<NewsData>?
}