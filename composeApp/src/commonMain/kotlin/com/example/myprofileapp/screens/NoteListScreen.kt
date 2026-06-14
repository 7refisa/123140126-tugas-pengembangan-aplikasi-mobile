package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.EmptyState
import com.example.myprofileapp.components.NoteCard
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NotesViewModel,
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onChatClick: () -> Unit,
    onMenuClick: () -> Unit = {},
    isDarkMode: Boolean,
    onToggleDark: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val sortOrder by viewModel.sortOrder.collectAsState()

    NoteListContent(
        notes            = notes,
        searchQuery      = searchQuery,
        sortOrder        = sortOrder,
        onSearchQueryChange = { viewModel.setSearchQuery(it) },
        onNoteClick      = onNoteClick,
        onAddClick       = onAddClick,
        onChatClick      = onChatClick,
        onMenuClick      = onMenuClick,
        onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
        isDarkMode       = isDarkMode,
        onToggleDark     = onToggleDark,
        onSetSortOrder   = { order -> viewModel.setSortOrder(order) }
    )
}

@Composable
fun NoteListContent(
    notes: List<Note>,
    searchQuery: String,
    sortOrder: String,
    onSearchQueryChange: (String) -> Unit,
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onChatClick: () -> Unit,
    onMenuClick: () -> Unit,
    onToggleFavorite: (Long) -> Unit,
    isDarkMode: Boolean,
    onToggleDark: () -> Unit,
    onSetSortOrder: (String) -> Unit
) {
    var isSortMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { 
            NotesTopBar(
                title = "Catatan Saya", 
                onMenuClick = onMenuClick,
                actions = {
                    Box {
                        TextButton(onClick = { isSortMenuExpanded = true }) {
                            Text(
                                text = if (sortOrder == "newest") "Terbaru ▼" else "Terlama ▼", 
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        DropdownMenu(
                            expanded = isSortMenuExpanded,
                            onDismissRequest = { isSortMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Terbaru") },
                                onClick = {
                                    onSetSortOrder("newest")
                                    isSortMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Terlama") },
                                onClick = {
                                    onSetSortOrder("oldest")
                                    isSortMenuExpanded = false
                                }
                            )
                        }
                    }
                    IconButton(onClick = onToggleDark) {
                        Text(if (isDarkMode) "☀️" else "🌙", style = MaterialTheme.typography.titleMedium)
                    }
                }
            ) 
        },
        floatingActionButton = {
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                SmallFloatingActionButton(
                    onClick        = onChatClick,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor   = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier       = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.Face, contentDescription = "Smart Assistant")
                }
                FloatingActionButton(
                    onClick        = onAddClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor   = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah catatan")
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Cari catatan...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )
            
            if (notes.isEmpty()) {
                EmptyState(
                    message  = if (searchQuery.isNotEmpty()) "Tidak ada catatan yang cocok" else "Belum ada catatan.\nTekan + untuk mulai menulis!",
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier            = Modifier.weight(1f),
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
}