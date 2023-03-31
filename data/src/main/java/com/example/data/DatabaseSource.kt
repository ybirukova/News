package com.example.data

import com.example.data.database.NewsDao
import com.example.data.database.NewsEntity
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseSource @Inject constructor(private val newsDao: NewsDao) {

    fun getAllNews(): Observable<List<NewsEntity>> {
        return newsDao.getAllNews()
    }

    fun insertAllNews(vararg news: NewsEntity) {
        newsDao.insertAll(*news)
    }
}