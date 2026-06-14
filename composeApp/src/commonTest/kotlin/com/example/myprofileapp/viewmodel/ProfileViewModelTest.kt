package com.example.myprofileapp.viewmodel

import com.example.myprofileapp.local.SettingsManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
class ProfileViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ProfileViewModel
    private lateinit var mockSettingsManager: SettingsManager
    private val themeFlow = MutableStateFlow("light")

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockSettingsManager = mockk(relaxed = true)
        every { mockSettingsManager.themeFlow } returns themeFlow
        coEvery { mockSettingsManager.setTheme(any()) } answers {
            themeFlow.value = it.invocation.args[0] as String
        }
        viewModel = ProfileViewModel(mockSettingsManager)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state reflects settings theme`() = runTest {
        assertFalse(viewModel.uiState.value.isDarkMode)
        themeFlow.value = "dark"
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isDarkMode)
    }

    @Test
    fun `toggleDarkMode switches theme`() = runTest {
        viewModel.toggleDarkMode()
        advanceUntilIdle()
        coVerify { mockSettingsManager.setTheme("dark") }
        
        viewModel.toggleDarkMode()
        advanceUntilIdle()
        coVerify { mockSettingsManager.setTheme("light") }
    }

    @Test
    fun `openEditMode and closeEditMode update state correctly`() {
        assertFalse(viewModel.uiState.value.isEditMode)
        viewModel.openEditMode()
        assertTrue(viewModel.uiState.value.isEditMode)
        viewModel.closeEditMode()
        assertFalse(viewModel.uiState.value.isEditMode)
    }

    @Test
    fun `saveProfile updates all fields correctly`() {
        viewModel.saveProfile("New Name ", " New Bio", "email@test.com", " 123 ", "Location")
        val state = viewModel.uiState.value
        assertEquals("New Name", state.name)
        assertEquals("New Bio", state.bio)
        assertEquals("email@test.com", state.email)
        assertEquals("123", state.phone)
        assertEquals("Location", state.location)
        assertFalse(state.isEditMode)
    }

    @Test
    fun `updateContactField updates specific fields`() {
        viewModel.updateContactField("Email", " new@test.com ")
        assertEquals("new@test.com", viewModel.uiState.value.email)
        
        viewModel.updateContactField("Phone", "999")
        assertEquals("999", viewModel.uiState.value.phone)
        
        viewModel.updateContactField("Location", "Jakarta")
        assertEquals("Jakarta", viewModel.uiState.value.location)
    }
}
