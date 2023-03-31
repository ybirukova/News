package com.example.data.models

import com.squareup.moshi.Json

data class NewsResponse(
    @Json(name = "title") val title: String? = null,
    @Json(name = "author") val author: String? = null,
    @Json(name = "publishedAt") val date: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "urlToImage") val urlToImage: String? = null,
    @Json(name = "content") val content: String? = null
)