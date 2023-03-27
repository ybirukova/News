package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val newsId: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("publishedAt") val date: String,
    @ColumnInfo("content") val content: String
)