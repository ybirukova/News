package com.example.data

import com.example.data.mappers.EntityToDataNewsMapper
import com.example.data.mappers.ResponseToDataNewsMapper
import com.example.data.network.NewsService
import com.example.data.mappers.DataToEntityNewsMapper
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListRepositoryImpl @Inject constructor(
    private val responseToDataMapper: ResponseToDataNewsMapper,
    private val entityToDataMapper: EntityToDataNewsMapper,
    private val dataToEntityNewsMapper: DataToEntityNewsMapper,
    private val service: NewsService,
    private val database: DatabaseSource,
) : NewsListRepository {

    override suspend fun getNewsList(isNetworkConnection: Boolean): List<NewsData> {
        return if (isNetworkConnection) {
            withContext(Dispatchers.IO) {
                val list = service.getNewsList().execute()
                    .body()?.articles?.map { responseToDataMapper(it) }
                    ?: throw Exception()
                val listOfEntities = list.map { dataToEntityNewsMapper(it) }
                database.insertAllNews(*listOfEntities.toTypedArray())
                list
            }
        } else withContext(Dispatchers.IO) {
            database.getAllNews().map { entityToDataMapper(it) }
        }
    }

    override suspend fun getNewsListBySearching(
        isNetworkConnection: Boolean,
        q: String
    ): List<NewsData> {
        return if (isNetworkConnection) {
            withContext(Dispatchers.IO) {
                service.getNewsListBySearching(q).execute()
                    .body()?.articles?.map { responseToDataMapper(it) }
                    ?: throw Exception()
            }
        } else withContext(Dispatchers.IO) {
            database.getAllNews().map { entityToDataMapper(it) }
        }
    }
}