package com.example.myprofileapp.ai

import io.ktor.client.plugins.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException

sealed class AIError : Exception() {
    data class RateLimited(val retryAfter: Int) : AIError()
    data class Unauthorized(override val message: String) : AIError()
    data class ServerError(override val message: String) : AIError()
    data class NetworkError(override val message: String) : AIError()
    data class ParseError(override val message: String) : AIError()
}

suspend fun <T> safeAICall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        if (e is kotlinx.coroutines.CancellationException) throw e
        when (e) {
            is ClientRequestException -> {
                when (e.response.status.value) {
                    401 -> Result.failure(AIError.Unauthorized("Invalid API key"))
                    429 -> {
                        val retryAfter = e.response.headers["Retry-After"]?.toIntOrNull() ?: 60
                        Result.failure(AIError.RateLimited(retryAfter))
                    }
                    in 500..599 -> Result.failure(AIError.ServerError("Server error"))
                    else -> Result.failure(e)
                }
            }
            is IOException -> Result.failure(AIError.NetworkError("No internet connection"))
            is SerializationException -> Result.failure(AIError.ParseError("Failed to parse response"))
            else -> Result.failure(e)
        }
    }
}
