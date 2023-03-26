package com.example.data

import com.example.data.mappers.DataToEntityNewsMapper
import com.example.data.mappers.EntityToDataNewsMapper
import com.example.data.mappers.ResponseToDataNewsMapper
import com.example.data.network.NewsService
import com.example.domain.models.NewsData
import com.example.domain.repository.NewsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListRepositoryImpl @Inject constructor(
    private val responseToDataMapper: ResponseToDataNewsMapper,
    private val entityToDataMapper: EntityToDataNewsMapper,
    private val dataToEntityNewsMapper: DataToEntityNewsMapper,
    private val service: NewsService,
    private val database: DatabaseSource,
) : NewsListRepository {

    override suspend fun getNewsListFromApiToDatabase(): List<NewsData> {
        return withContext(Dispatchers.IO) {
            val list = service.getNewsList().execute()
                .body()?.articles?.map { responseToDataMapper(it) }
                ?: throw Exception()
            val listOfEntities = list.map { dataToEntityNewsMapper(it) }
            database.insertAllNews(*listOfEntities.toTypedArray())
            list
        }
    }

    override suspend fun getNewsList(isNetworkConnection: Boolean): Flow<List<NewsData>> {
        return withContext(Dispatchers.IO) {
            database.getAllNews().map { list ->
                list.map {
                    entityToDataMapper(it)
                }
            }
        }
    }

    override suspend fun getNewsListBySearching(
        isNetworkConnection: Boolean,
        q: String
    ): Flow<List<NewsData>> {
        return withContext(Dispatchers.IO) {
            val list = service.getNewsListBySearching(q).execute()
                .body()?.articles?.map { responseToDataMapper(it) }
                ?: throw Exception()
            flow { emit(list) }
        }
    }
}