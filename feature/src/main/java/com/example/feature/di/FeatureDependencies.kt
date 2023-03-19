package com.example.feature.di

import com.example.domain.repository.NewsListRepository
import com.example.ui_kit.NetworkConnection

interface FeatureDependencies {

    fun repository(): NewsListRepository

    fun networkConnection(): NetworkConnection
}