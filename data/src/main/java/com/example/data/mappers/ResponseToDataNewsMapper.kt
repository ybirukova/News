package com.example.data.mappers

import com.example.data.models.NewsResponse
import com.example.domain.models.NewsData
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