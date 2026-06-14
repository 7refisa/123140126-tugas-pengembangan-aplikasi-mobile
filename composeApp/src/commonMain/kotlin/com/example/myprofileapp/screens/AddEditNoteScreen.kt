package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.utils.TestTags
import com.example.myprofileapp.viewmodel.NotesViewModel

// ── ADD NOTE ─────────────────────────────────────────────────────────────────

@Composable
fun AddNoteScreen(
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    var title   by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = { NotesTopBar(title = "Catatan Baru", onBack = onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value         = title,
                onValueChange = { title = it },
                label         = { Text("Judul") },
                placeholder   = { Text("Masukkan judul catatan...") },
                modifier      = Modifier.fillMaxWidth().testTag(TestTags.TITLE_INPUT),
                singleLine    = true,
                shape         = MaterialTheme.shapes.medium
            )

            OutlinedTextField(
                value         = content,
                onValueChange = { content = it },
                label         = { Text("Isi Catatan") },
                placeholder   = { Text("Tulis catatan di sini...") },
                modifier      = Modifier.fillMaxWidth().heightIn(min = 200.dp).testTag(TestTags.CONTENT_INPUT),
                maxLines      = 15,
                shape         = MaterialTheme.shapes.medium
            )

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.addNote(title.trim(), content.trim())
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().testTag(TestTags.ADD_BUTTON),
                enabled  = title.isNotBlank()
            ) {
                Text(
                    text       = "Simpan Catatan",
                    fontWeight = FontWeight.SemiBold,
                    modifier   = Modifier.padding(vertical = 4.dp)
                )
            }

            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Batal")
            }
        }
    }
}

// ── EDIT NOTE ─────────────────────────────────────────────────────────────────

@Composable
fun EditNoteScreen(
    noteId: Long,
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(noteId) {
        viewModel.selectNote(noteId)
    }

    val existing by viewModel.selectedNote.collectAsState()

    var title   by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(existing) {
        if (existing != null) {
            title = existing!!.title
            content = existing!!.content
        }
    }

    Scaffold(
        topBar = { NotesTopBar(title = "Edit Catatan", onBack = {
            viewModel.clearSelectedNote()
            onBack()
        }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value         = title,
                onValueChange = { title = it },
                label         = { Text("Judul") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true,
                shape         = MaterialTheme.shapes.medium
            )

            OutlinedTextField(
                value         = content,
                onValueChange = { content = it },
                label         = { Text("Isi Catatan") },
                modifier      = Modifier.fillMaxWidth().heightIn(min = 200.dp),
                maxLines      = 15,
                shape         = MaterialTheme.shapes.medium
            )

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.updateNote(noteId, title.trim(), content.trim())
                        viewModel.clearSelectedNote()
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled  = title.isNotBlank()
            ) {
                Text(
                    text       = "Simpan Perubahan",
                    fontWeight = FontWeight.SemiBold,
                    modifier   = Modifier.padding(vertical = 4.dp)
                )
            }

            OutlinedButton(onClick = {
                viewModel.clearSelectedNote()
                onBack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Batal")
            }
        }
    }
}