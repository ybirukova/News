package com.example.data

import com.example.data.database.NewsEntity
import com.example.data.mappers.DataToEntityNewsMapper
import com.example.domain.models.NewsData
import org.junit.Assert
import org.junit.Test

class DataToEntityMapperTest {
    @Test
    fun `is mapper works correct with all values`() {
        val mapper = DataToEntityNewsMapper()
        val data = NewsData(
            "some title",
            "Author",
            "12.11.2020",
            "http://",
            "http://image",
            "some content"
        )
        val entity = NewsEntity(
            0,
            "some title",
            "Author",
            "12.11.2020",
            "http://",
            "http://image",
            "some content"
        )

        Assert.assertEquals(entity, mapper(data))
    }

    @Test
    fun `is mapper works correct if some strings is blank`() {
        val mapper = DataToEntityNewsMapper()
        val data = NewsData(
            "some title",
            "Author",
            "",
            "",
            "http://image",
            "some content"
        )
        val entity = NewsEntity(
            0,
            "some title",
            "Author",
            "",
            "",
            "http://image",
            "some content"
        )

        Assert.assertEquals(entity, mapper(data))
    }
}