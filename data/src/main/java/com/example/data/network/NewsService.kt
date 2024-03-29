package com.example.data.network

import com.example.data.models.NewsListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {

    @Headers("x-api-key: 39df625e36f64f91b20589725570a891")
    @GET("everything?q=nature")
    fun getNewsList(): Call<NewsListResponse>

    @Headers("x-api-key: 39df625e36f64f91b20589725570a891")
    @GET("everything")
    fun getNewsListBySearching(@Query("q") search: String): Call<NewsListResponse>
}