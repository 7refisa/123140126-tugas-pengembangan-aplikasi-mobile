package com.example.myprofileapp.ai

import com.example.myprofileapp.platform.ApiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class GeminiService(private val client: HttpClient) {
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-flash-latest"
    
    suspend fun generateContent(prompt: String): Result<String> = runCatching {
        val request = GeminiRequest(
            contents = listOf(
                Content(parts = listOf(Part(text = prompt)))
            ),
            generationConfig = GenerationConfig(
                maxOutputTokens = 1000,
                temperature = 0.7
            )
        )
        
        val response = client.post("$baseUrl/models/$model:generateContent") {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey)
            setBody(request)
        }
        
        if (response.status.isSuccess()) {
            val body: GeminiResponse = response.body()
            body.candidates.first().content.parts.first().text
        } else {
            val errorText = response.bodyAsText()
            error("API Error ${response.status.value}: $errorText")
        }
    }
}
