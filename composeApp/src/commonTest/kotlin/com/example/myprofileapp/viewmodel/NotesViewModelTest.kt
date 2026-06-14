package com.example.myprofileapp.viewmodel

import app.cash.turbine.test
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NoteRepository
import com.example.myprofileapp.local.SettingsManager
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var repository: NoteRepository
    private lateinit var settingsManager: SettingsManager
    private lateinit var viewModel: NotesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        settingsManager = mockk()

        val mockSortOrderFlow = MutableStateFlow("newest")
        every { settingsManager.sortOrderFlow } returns mockSortOrderFlow
        coEvery { settingsManager.setSortOrder(any()) } answers {
            mockSortOrderFlow.value = firstArg()
        }

        every { repository.getAllNotes(any()) } returns flowOf(emptyList())

        viewModel = NotesViewModel(repository, settingsManager)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addNote calls repository insertNote`() = runTest {
        // Arrange
        coEvery { repository.insertNote(any(), any(), any()) } just Runs

        // Act
        viewModel.addNote("New Title", "New Content")
        runCurrent()

        // Assert
        coVerify { repository.insertNote(eq("New Title"), eq("New Content"), any()) }
    }

    @Test
    fun `deleteNote calls repository deleteNote`() = runTest {
        // Arrange
        coEvery { repository.deleteNote(any()) } just Runs

        // Act
        viewModel.deleteNote(10L)
        runCurrent()

        // Assert
        coVerify { repository.deleteNote(10L) }
    }

    @Test
    fun `notes flow emits notes from repository using Turbine`() = runTest {
        // Arrange
        val testNote = Note(1L, "Test Note", "Content", false, "1 Januari 2026")
        every { repository.getAllNotes(any()) } returns flowOf(listOf(testNote))
        
        // Re-initialize ViewModel so it uses the mocked flow properly
        viewModel = NotesViewModel(repository, settingsManager)

        // Act & Assert using Turbine
        viewModel.notes.test {
            val emittedNotes = awaitItem()
            assertEquals(1, emittedNotes.size)
            assertEquals("Test Note", emittedNotes[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleSortOrder changes sortOrder flow value using Turbine`() = runTest {
        // Act & Assert using Turbine
        viewModel.sortOrder.test {
            // Initial state from settingsManager mock
            assertEquals("newest", awaitItem())
            
            // Toggle
            viewModel.toggleSortOrder()
            
            // Wait for the new value
            assertEquals("oldest", awaitItem())
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}
