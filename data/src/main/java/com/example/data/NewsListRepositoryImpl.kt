package com.example.data

import android.util.Log
import com.example.data.mappers.DataToEntityNewsMapper
import com.example.data.mappers.EntityToDataNewsMapper
import com.example.data.mappers.ResponseToDataNewsMapper
import com.example.data.network.NewsService
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class NewsListRepositoryImpl @Inject constructor(
    private val responseToDataMapper: ResponseToDataNewsMapper,
    private val entityToDataMapper: EntityToDataNewsMapper,
    private val dataToEntityNewsMapper: DataToEntityNewsMapper,
    private val service: NewsService,
    private val database: DatabaseSource,
) : NewsListRepository {

    override fun getNewsListFromApiToDatabase(): Completable {

        return service.getNewsList().flatMapCompletable {
            val list = it.articles?.map { responseToDataMapper(it) } ?: throw Exception()
            val listOfEntities = list.map { dataToEntityNewsMapper(it) }
            database.insertAllNews(*listOfEntities.toTypedArray())
            Completable.complete()
        }
    }

    override fun getNewsList(isNetworkConnection: Boolean): Observable<List<NewsData>> {
        val list = database.getAllNews().map { list ->
            Log.d("PRINT1", list.toString())
            list.map { entityToDataMapper(it) }
        }
        return list
    }

    override fun getNewsListBySearching(
        isNetworkConnection: Boolean,
        q: String
    ): Single<List<NewsData>> {

        return service.getNewsListBySearching(q).map {
            val list = it.articles?.map { responseToDataMapper(it) } ?: throw Exception()
            list
        }
    }
}