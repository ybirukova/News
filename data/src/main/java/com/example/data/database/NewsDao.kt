package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_table")
    fun getAllNews(): Observable<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: NewsEntity)
}