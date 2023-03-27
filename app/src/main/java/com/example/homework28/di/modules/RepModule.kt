package com.example.homework28.di.modules

import com.example.data.NewsListRepositoryImpl
import com.example.domain.repository.NewsListRepository
import dagger.Binds
import dagger.Module

@Module
interface RepModule {

    @Binds
    fun bindRep(impl: NewsListRepositoryImpl): NewsListRepository
}