package com.example.domain.repository

import com.example.domain.models.NewsData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NewsListRepository {

    fun getNewsListFromApiToDatabase(): Completable

    fun getNewsList(isNetworkConnection: Boolean): Observable<List<NewsData>>

    fun getNewsListBySearching(
        isNetworkConnection: Boolean,
        q: String
    ): Single<List<NewsData>>
}