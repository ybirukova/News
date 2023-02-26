package com.example.homework28.di

import android.content.Context
import com.example.homework28.data.network.NewsService
import com.example.homework28.ui.utils.NetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    @Provides
    fun getService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    fun getNetworkConnection(@ApplicationContext context: Context): NetworkConnection{
        return NetworkConnection(context)
    }

    companion object {
        const val baseUrl = "https://newsapi.org/v2/"
    }
}