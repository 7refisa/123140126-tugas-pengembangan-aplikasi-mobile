package com.example.myprofileapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.EditActionButton
import com.example.myprofileapp.components.FavoriteActionButton
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.NoteRepository

@Composable
fun NoteDetailScreen(
    noteId: Int,
    onBack: () -> Unit,
    onEditClick: (Int) -> Unit
) {
    // Baca langsung dari repository — otomatis update saat toggle favorit
    val note = NoteRepository.findById(noteId)

    Scaffold(
        topBar = {
            NotesTopBar(
                title  = "Detail Catatan",
                onBack = onBack,
                actions = {
                    // Tombol toggle favorit di TopAppBar
                    FavoriteActionButton(
                        isFavorite = note?.isFavorite ?: false,
                        onClick    = { NoteRepository.toggleFavorite(noteId) }
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
                    Text("Catatan tidak ditemukan.", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBack) { Text("Kembali") }
                }
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text       = note.title,
                style      = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (note.isFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint               = if (note.isFavorite) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    modifier           = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text  = if (note.isFavorite) "Favorit" else "Bukan Favorit",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (note.isFavorite) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
                )
            }

            Text(
                text  = "Dibuat: ${note.createdAt}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )

            HorizontalDivider()

            Text(
                text  = note.content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}