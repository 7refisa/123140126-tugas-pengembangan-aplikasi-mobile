package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.EmptyState
import com.example.myprofileapp.components.NoteCard
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.NoteRepository

@Composable
fun FavoritesScreen(onNoteClick: (Int) -> Unit) {
    val favorites = NoteRepository.notes.filter { it.isFavorite }

    Scaffold(
        topBar = { NotesTopBar(title = "Favorit") }
    ) { innerPadding ->
        if (favorites.isEmpty()) {
            EmptyState(
                message  = "Belum ada catatan favorit.\nTekan ♥ pada catatan untuk menyimpannya.",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier            = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(favorites, key = { it.id }) { note ->
                    NoteCard(
                        note             = note,
                        onClick          = { onNoteClick(note.id) },
                        onToggleFavorite = { NoteRepository.toggleFavorite(note.id) }
                    )
                }
            }
        }
    }
}