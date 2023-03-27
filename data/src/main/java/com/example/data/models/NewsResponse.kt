package com.example.data.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("title") val title: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("publishedAt") val date: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("content") val content: String? = null
)