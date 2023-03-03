package com.example.homework28.data

import com.example.homework28.data.database.DatabaseSource
import com.example.homework28.data.database.mappers.EntityToDataNewsMapper
import com.example.homework28.data.mappers.ResponseToDataNewsMapper
import com.example.homework28.data.network.NewsService
import com.example.homework28.domain.mappers.DataToEntityNewsMapper
import com.example.homework28.domain.models.NewsData
import com.example.homework28.domain.repository.NewsListRepository
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
                val list = service.getNewsList().execute().body()?.articles?.map { responseToDataMapper(it) }
                    ?: throw Exception()
                val listOfEntities = list.map { dataToEntityNewsMapper(it) }
                database.insertAllNews(*listOfEntities.toTypedArray())
                list
            }
        } else withContext(Dispatchers.IO) {
            database.getAllNews().map { entityToDataMapper(it) }
        }
    }
}