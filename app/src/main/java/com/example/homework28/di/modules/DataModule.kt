package com.example.homework28.di.modules

import com.example.homework28.data.NewsListRepositoryImpl
import com.example.homework28.domain.repository.NewsListRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun getNewsListRepository(impl: NewsListRepositoryImpl): NewsListRepository
}