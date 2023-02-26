package com.example.homework28.di

import android.content.Context
import androidx.room.Room
import com.example.homework28.data.database.AppDataBase
import com.example.homework28.data.database.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "news_database")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun getNewsDao(db: AppDataBase): NewsDao = db.getNewsDao()
}