package com.example.myprofileapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myprofileapp.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NoteRepository(private val database: NotesDatabase) {
    private val queries = database.noteQueries

    fun getAllNotes(newestFirst: Boolean): Flow<List<Note>> {
        val query = if (newestFirst) queries.selectAllNewest() else queries.selectAllOldest()
        return query
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toUiModel() } }
    }

    fun searchNotes(query: String, newestFirst: Boolean): Flow<List<Note>> {
        val searchQuery = if (newestFirst) queries.searchNewest(query) else queries.searchOldest(query)
        return searchQuery
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toUiModel() } }
    }

    suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOneOrNull()?.toUiModel()
        }
    }

    suspend fun insertNote(title: String, content: String, isFavorite: Boolean = false) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.insert(title, content, isFavorite, now)
        }
    }

    suspend fun updateNote(id: Long, title: String, content: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            queries.update(title, content, isFavorite, id)
        }
    }

    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }

    private fun com.example.myprofileapp.db.NoteEntity.toUiModel(): Note {
        val instant = Instant.fromEpochMilliseconds(this.createdAt)
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val months = listOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
        val formattedDate = "${localDate.dayOfMonth} ${months[localDate.monthNumber - 1]} ${localDate.year}"
        
        return Note(
            id = this.id,
            title = this.title,
            content = this.content,
            isFavorite = this.isFavorite,
            createdAt = formattedDate
        )
    }
}