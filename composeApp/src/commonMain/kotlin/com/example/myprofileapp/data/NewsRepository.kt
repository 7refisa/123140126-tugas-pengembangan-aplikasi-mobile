package com.example.myprofileapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NewsRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private const val BASE_URL = "https://saurav.tech/NewsAPI/top-headlines/category/technology/us.json"

    // Simulate network delay for testing loading states if needed
    // private const val SIMULATE_DELAY = 1000L

    suspend fun getNews(): Result<List<Article>> {
        return try {
            // kotlinx.coroutines.delay(SIMULATE_DELAY)
            val response: NewsResponse = client.get(BASE_URL).body()
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
