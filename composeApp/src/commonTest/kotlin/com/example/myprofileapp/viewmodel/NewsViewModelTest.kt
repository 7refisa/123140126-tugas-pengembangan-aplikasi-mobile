package com.example.myprofileapp.viewmodel

import com.example.myprofileapp.data.Article
import com.example.myprofileapp.data.NewsRepository
import io.mockk.coEvery
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(NewsRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkObject(NewsRepository)
    }

    @Test
    fun `fetchNews updates uiState to Success on valid response`() = runTest {
        val fakeArticles = listOf(
            Article("Title 1", "Desc 1", "urlToImage1", "url1"),
            Article("Title 2", "Desc 2", "urlToImage2", "url2")
        )
        coEvery { NewsRepository.getNews() } returns Result.success(fakeArticles)

        val viewModel = NewsViewModel()
        
        // Wait for coroutines to complete
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is NewsUiState.Success)
        assertEquals(fakeArticles, state.articles)
    }

    @Test
    fun `fetchNews updates uiState to Error on failure`() = runTest {
        coEvery { NewsRepository.getNews() } returns Result.failure(Exception("Network error"))

        val viewModel = NewsViewModel()
        
        // Wait for coroutines to complete
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is NewsUiState.Error)
        assertEquals("Network error", state.message)
    }

    @Test
    fun `refreshNews updates uiState correctly`() = runTest {
        val fakeArticles = listOf(Article("New Title", "New Desc", "NewImage", "NewUrl"))
        coEvery { NewsRepository.getNews() } returns Result.success(fakeArticles)

        val viewModel = NewsViewModel()
        advanceUntilIdle() // let init block finish

        // Trigger refresh
        viewModel.refreshNews()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is NewsUiState.Success)
        assertEquals(fakeArticles, state.articles)
        assertFalse(viewModel.isRefreshing.value)
    }
}
