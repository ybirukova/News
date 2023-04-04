package com.example.data.mappers

import com.example.data.database.NewsEntity
import com.example.domain.models.NewsData
import javax.inject.Inject

class DataToEntityNewsMapper @Inject constructor() {

    operator fun invoke(data: NewsData) = with(data) {
        NewsEntity(
            title = title,
            author = author,
            date = date,
            url = url,
            urlToImage = urlToImage,
            content = content
        )
    }
}