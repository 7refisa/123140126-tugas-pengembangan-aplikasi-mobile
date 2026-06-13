package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.Article
import com.example.myprofileapp.data.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            val result = NewsRepository.getNews()
            result.onSuccess { articles ->
                _uiState.value = NewsUiState.Success(articles)
            }.onFailure { error ->
                _uiState.value = NewsUiState.Error(error.message ?: "Terjadi kesalahan saat memuat berita")
            }
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val result = NewsRepository.getNews()
            result.onSuccess { articles ->
                _uiState.value = NewsUiState.Success(articles)
            }.onFailure { error ->
                _uiState.value = NewsUiState.Error(error.message ?: "Gagal memperbarui berita")
            }
            _isRefreshing.value = false
        }
    }
}
