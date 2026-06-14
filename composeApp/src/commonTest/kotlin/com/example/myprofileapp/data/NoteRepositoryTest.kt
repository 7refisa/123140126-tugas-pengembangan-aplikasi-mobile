package com.example.myprofileapp.data

import app.cash.sqldelight.Query
import com.example.myprofileapp.db.NoteEntity
import com.example.myprofileapp.db.NoteQueries
import com.example.myprofileapp.db.NotesDatabase
import io.mockk.*
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NoteRepositoryTest {
    private lateinit var database: NotesDatabase
    private lateinit var queries: NoteQueries
    private lateinit var repository: NoteRepository

    @BeforeTest
    fun setup() {
        database = mockk()
        queries = mockk()
        every { database.noteQueries } returns queries
        repository = NoteRepository(database)
    }

    @Test
    fun `insertNote calls queries insert with correct parameters`() = runTest {
        // Arrange
        every { queries.insert(any(), any(), any(), any()) } just Runs

        // Act
        repository.insertNote("Test Title", "Test Content", true)

        // Assert
        verify { queries.insert(eq("Test Title"), eq("Test Content"), eq(true), any()) }
    }

    @Test
    fun `updateNote calls queries update with correct parameters`() = runTest {
        // Arrange
        every { queries.update(any(), any(), any(), any()) } just Runs

        // Act
        repository.updateNote(1L, "Updated Title", "Updated Content", false)

        // Assert
        verify { queries.update(eq("Updated Title"), eq("Updated Content"), eq(false), eq(1L)) }
    }

    @Test
    fun `deleteNote calls queries delete with correct id`() = runTest {
        // Arrange
        every { queries.delete(any()) } just Runs

        // Act
        repository.deleteNote(5L)

        // Assert
        verify { queries.delete(eq(5L)) }
    }

    @Test
    fun `getNoteById returns mapped Note when found`() = runTest {
        // Arrange
        val mockQuery = mockk<Query<NoteEntity>>()
        val fakeEntity = NoteEntity(1L, "Test Title", "Test Content", false, 1718300000000L)
        
        every { queries.selectById(1L) } returns mockQuery
        every { mockQuery.executeAsOneOrNull() } returns fakeEntity

        // Act
        val result = repository.getNoteById(1L)

        // Assert
        assertEquals(1L, result?.id)
        assertEquals("Test Title", result?.title)
        assertEquals("Test Content", result?.content)
        assertEquals(false, result?.isFavorite)
        verify { queries.selectById(1L) }
        verify { mockQuery.executeAsOneOrNull() }
    }

    @Test
    fun `getNoteById returns null when not found`() = runTest {
        // Arrange
        val mockQuery = mockk<Query<NoteEntity>>()
        
        every { queries.selectById(99L) } returns mockQuery
        every { mockQuery.executeAsOneOrNull() } returns null

        // Act
        val result = repository.getNoteById(99L)

        // Assert
        assertNull(result)
        verify { queries.selectById(99L) }
        verify { mockQuery.executeAsOneOrNull() }
    }
}
