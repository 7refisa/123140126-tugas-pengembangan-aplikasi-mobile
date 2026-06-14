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
                    "Kamu adalah asisten AI yang ramah dan sangat membantu. Gunakan bahasa Indonesia yang natural, bersahabat, dan tidak kaku (gunakan sapaan 'aku' dan 'kamu'). Jangan mencoba terlalu gaul atau sok asik, jawab senatural mungkin dan langsung ke intinya. JANGAN gunakan format markdown seperti bintang (*) atau (**). Jika perlu membuat list, gunakan simbol bullet (•) atau strip (-) dan WAJIB berikan jarak SATU BARIS KOSONG antar poin list agar teksnya tidak menumpuk dan mudah dibaca.\\n$promptStr"
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
