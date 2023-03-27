package com.example.data

import com.example.data.database.NewsDao
import com.example.data.database.NewsEntity
import javax.inject.Inject

class DatabaseSource @Inject constructor(private val newsDao: NewsDao) {

    fun getAllNews(): List<NewsEntity> {
        return newsDao.getAllNews()
    }

    fun insertAllNews(vararg news: NewsEntity) {
        newsDao.insertAll(*news)
    }
}