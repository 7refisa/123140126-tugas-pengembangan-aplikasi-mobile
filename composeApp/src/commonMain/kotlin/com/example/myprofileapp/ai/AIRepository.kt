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
                    "Kamu adalah asisten AI yang ramah. JAWAB DENGAN SANGAT SINGKAT, PADAT, DAN LANGSUNG KE INTI (Straight to the point). JANGAN gunakan kalimat basa-basi atau pembuka yang panjang. Gunakan bahasa Indonesia yang natural (sapaan 'aku' dan 'kamu'). JANGAN gunakan format markdown seperti bintang (*) atau (**). Jika membuat list, gunakan simbol bullet (•) atau strip (-) dan berikan jarak SATU BARIS KOSONG antar poin list.\\n$promptStr"
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
