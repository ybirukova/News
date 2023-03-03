package com.example.homework28.domain.mappers

import com.example.homework28.data.database.NewsEntity
import com.example.homework28.domain.models.NewsData
import javax.inject.Inject

class DataToEntityNewsMapper @Inject constructor() {

    operator fun invoke(data: NewsData) = with(data) {
        NewsEntity(
            title = title,
            author = author,
            date = (date.substring(0, 10)),
            content = content
        )
    }
}