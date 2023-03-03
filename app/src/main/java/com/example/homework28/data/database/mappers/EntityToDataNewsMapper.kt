package com.example.homework28.data.database.mappers

import com.example.homework28.data.database.NewsEntity
import com.example.homework28.domain.models.NewsData
import javax.inject.Inject

class EntityToDataNewsMapper @Inject constructor() {

    operator fun invoke(entity: NewsEntity) = with(entity) {
        NewsData(
            title = title,
            author = author,
            date = (date.substring(0, 10)),
            url = EMPTY_STRING,
            urlToImage = EMPTY_STRING,
            content = content
        )
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}