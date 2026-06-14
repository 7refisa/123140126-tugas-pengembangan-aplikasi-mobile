package com.example.myprofileapp.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ChatMessage(
    val role: String, // "user" or "assistant"
    val content: String
)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel(
    private val aiRepository: AIRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Initial greeting
        _uiState.update { it.copy(
            messages = listOf(
                ChatMessage("assistant", "Halo! Saya adalah Smart Assistant Anda. Ada yang bisa saya bantu hari ini?")
            )
        ) }
    }
    
    fun sendMessage(message: String) {
        if (message.isBlank()) return
        
        _uiState.update { it.copy(
            messages = it.messages + ChatMessage(role = "user", content = message),
            isLoading = true,
            error = null
        )}
        
        viewModelScope.launch {
            aiRepository.chat(message)
                .onSuccess { response ->
                    _uiState.update { it.copy(
                        messages = it.messages + ChatMessage(role = "assistant", content = response),
                        isLoading = false
                    )}
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        error = error.message ?: "Terjadi kesalahan. Silakan coba lagi.",
                        isLoading = false
                    )}
                }
        }
    }
}
