package com.example.homework28.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.database.AppDataBase
import com.example.data.database.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "news_database")
            .build()
    }

    @Provides
    @Singleton
    fun getNewsDao(db: AppDataBase): NewsDao = db.getNewsDao()
}