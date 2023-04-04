package com.example.data

import com.example.data.mappers.ResponseToDataNewsMapper
import com.example.data.models.NewsResponse
import com.example.domain.models.NewsData
import org.junit.Assert
import org.junit.Test

class ResponseToDataMapperTest {
    @Test
    fun `is mapperToDataNewsMapper() works correct with all values`() {
        val mapper = ResponseToDataNewsMapper()
        val response = NewsResponse(
            "some title",
            "Author",
            "12.11.2020 14:40",
            "http://",
            "http://image",
            "some content"
        )
        val domain = NewsData(
            "some title",
            "Author",
            "12.11.2020",
            "http://",
            "http://image",
            "some content"
        )

        Assert.assertEquals(domain, mapper(response))
    }

    @Test
    fun `is mapper works correct if some values is empty or null`() {
        val mapper = ResponseToDataNewsMapper()
        val response = NewsResponse(
            null,
            "",
            "12.11.2020",
            null,
            "http://image",
            "some content"
        )
        val domain = NewsData(
            "",
            "",
            "12.11.2020",
            "",
            "http://image",
            "some content"
        )

        Assert.assertEquals(domain, mapper(response))
    }
}