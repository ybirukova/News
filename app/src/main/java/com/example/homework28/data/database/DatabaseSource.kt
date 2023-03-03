package com.example.homework28.data.database

import javax.inject.Inject

class DatabaseSource @Inject constructor(private val newsDao: NewsDao) {

    fun getAllNews(): List<NewsEntity> {
        return newsDao.getAllNews()
    }

    fun insertAllNews(vararg news: NewsEntity) {
        newsDao.insertAll(*news)
    }
}