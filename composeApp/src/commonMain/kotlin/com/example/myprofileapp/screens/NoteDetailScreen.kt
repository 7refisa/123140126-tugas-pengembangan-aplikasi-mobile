package com.example.myprofileapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.EditActionButton
import com.example.myprofileapp.components.FavoriteActionButton
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.viewmodel.NotesViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NotesViewModel,
    onBack: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    LaunchedEffect(noteId) {
        viewModel.selectNote(noteId)
    }

    val note by viewModel.selectedNote.collectAsState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NotesTopBar(
                title  = "Detail Catatan",
                onBack = {
                    viewModel.clearSelectedNote()
                    onBack()
                },
                actions = {
                    // Tombol delete
                    IconButton(onClick = { showDeleteConfirm = true }) {
                        Icon(androidx.compose.material.icons.Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
                    }
                    // Tombol toggle favorit di TopAppBar
                    FavoriteActionButton(
                        isFavorite = note?.isFavorite ?: false,
                        onClick    = {
                            viewModel.toggleFavorite(noteId)
                            viewModel.selectNote(noteId) // Refresh selected note
                        }
                    )
                    // Tombol edit
                    EditActionButton(onClick = { onEditClick(noteId) })
                }
            )
        }
    ) { innerPadding ->

        if (note == null) {
            Box(
                modifier         = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⚠️", style = MaterialTheme.typography.displayMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Memuat catatan...", color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        viewModel.clearSelectedNote()
                        onBack()
                    }) { Text("Kembali") }
                }
            }
            return@Scaffold
        }

        val currentNote = note!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text       = currentNote.title,
                style      = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (currentNote.isFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint               = if (currentNote.isFavorite) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    modifier           = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text  = if (currentNote.isFavorite) "Favorit" else "Bukan Favorit",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (currentNote.isFavorite) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
                )
            }

            Text(
                text  = "Dibuat: ${currentNote.createdAt}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )

            HorizontalDivider()

            Text(
                text  = currentNote.content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Hapus Catatan") },
            text = { Text("Apakah kamu yakin ingin menghapus catatan ini?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteNote(noteId)
                    showDeleteConfirm = false
                    viewModel.clearSelectedNote()
                    onBack()
                }) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Batal")
                }
            }
        )
    }
}