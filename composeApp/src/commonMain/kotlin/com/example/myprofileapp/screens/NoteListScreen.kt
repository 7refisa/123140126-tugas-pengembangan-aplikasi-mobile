package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.EmptyState
import com.example.myprofileapp.components.NoteCard
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NoteRepository

@Composable
fun NoteListScreen(
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    // Langsung baca dari NoteRepository.notes yang reaktif
    val notes = NoteRepository.notes.toList()

    NoteListContent(
        notes            = notes,
        onNoteClick      = onNoteClick,
        onAddClick       = onAddClick,
        onToggleFavorite = { id -> NoteRepository.toggleFavorite(id) }
    )
}

@Composable
fun NoteListContent(
    notes: List<Note>,
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Scaffold(
        topBar = { NotesTopBar(title = "Catatan Saya") },
        floatingActionButton = {
            FloatingActionButton(
                onClick        = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor   = MaterialTheme.colorScheme.onPrimary,
                modifier       = Modifier.padding(bottom = 56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah catatan")
            }
        }
    ) { innerPadding ->
        if (notes.isEmpty()) {
            EmptyState(
                message  = "Belum ada catatan.\nTekan + untuk mulai menulis!",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier            = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteCard(
                        note             = note,
                        onClick          = { onNoteClick(note.id) },
                        onToggleFavorite = { onToggleFavorite(note.id) }
                    )
                }
            }
        }
    }
}