package com.example.myprofileapp.ai

import kotlinx.coroutines.delay

interface AIRepository {
    suspend fun chat(message: String): Result<String>
    suspend fun summarize(text: String): Result<String>
}

class AIRepositoryImpl(
    private val geminiService: GeminiService
) : AIRepository {

    private val conversationHistory = mutableListOf<Content>()

    override suspend fun chat(message: String): Result<String> {
        conversationHistory.add(
            Content(parts = listOf(Part(text = message)), role = "user")
        )
        
        return safeAICall {
            retryWithBackoff {
                val promptStr = conversationHistory.joinToString("\\n") { "${it.role}: ${it.parts.first().text}" }
                
                val response = geminiService.generateContent(
                    "You are a helpful AI assistant focused on productivity and note-taking. Tolong jawab dengan SINGKAT, PADAT, dan JELAS (maksimal 2-3 paragraf pendek). Jawab menggunakan teks biasa yang rapi TANPA format markdown (jangan gunakan tanda bintang `*` atau `**` untuk list maupun cetak tebal). Gunakan penomoran angka atau strip biasa (-). \\n$promptStr"
                ).getOrThrow()
                
                conversationHistory.add(
                    Content(parts = listOf(Part(text = response)), role = "model")
                )
                
                response
            }
        }
    }

    override suspend fun summarize(text: String): Result<String> {
        val prompt = """
            Rangkum teks berikut secara singkat dan jelas.
            Fokus pada poin-poin utama saja.
            
            Teks:
            $text
        """.trimIndent()
        
        return safeAICall {
            retryWithBackoff {
                geminiService.generateContent(prompt).getOrThrow()
            }
        }
    }

    private suspend fun <T> retryWithBackoff(
        times: Int = 3,
        initialDelay: Long = 1000,
        maxDelay: Long = 10000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) { attempt ->
            try {
                return block()
            } catch (e: Exception) {
                when (e) {
                    is AIError.RateLimited -> {
                        delay(e.retryAfter * 1000L)
                    }
                    is AIError.ServerError -> {
                        delay(currentDelay)
                        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
                    }
                    else -> throw e
                }
            }
        }
        return block()
    }
}
