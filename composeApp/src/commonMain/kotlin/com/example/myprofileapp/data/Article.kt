package com.example.myprofileapp.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article> = emptyList()
)

@Serializable
data class Article(
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val content: String? = null,
    val author: String? = null,
    val publishedAt: String? = null
)
