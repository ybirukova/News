package com.example.homework28.data.mappers

import com.example.homework28.data.models.NewsResponse
import com.example.homework28.domain.models.NewsData
import javax.inject.Inject

class ResponseToDataNewsMapper @Inject constructor() {

    operator fun invoke(response: NewsResponse) = with(response) {
        NewsData(
            title = title.orEmpty(),
            author = author.orEmpty(),
            date = (date?.substring(0, 10)).orEmpty(),
            url = url.orEmpty(),
            urlToImage = urlToImage.orEmpty(),
            content = content.orEmpty()
        )
    }
}