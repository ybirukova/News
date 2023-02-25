package com.example.homework28.domain.repository

import com.example.homework28.domain.models.NewsData

interface NewsListRepository {

    suspend fun getNewsList(): List<NewsData>
}