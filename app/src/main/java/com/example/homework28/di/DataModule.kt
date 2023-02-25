package com.example.homework28.di

import com.example.homework28.data.NewsListRepositoryImpl
import com.example.homework28.domain.repository.NewsListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun getNewsListRepository(impl: NewsListRepositoryImpl): NewsListRepository
}