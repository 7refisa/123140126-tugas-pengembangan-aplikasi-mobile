package com.example.myprofileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.NoteRepository

// ─────────────────────────────────────────────────────────────────────────────
// ProfileScreen — Tab "Profile" di Bottom Navigation
// ─────────────────────────────────────────────────────────────────────────────

/**
 * STATEFUL ProfileScreen.
 * Tidak membutuhkan callback navigasi karena tidak ada aksi navigasi
 * yang diperlukan dari screen ini untuk keperluan tugas minggu-5.
 */
@Composable
fun ProfileScreen() {
    val totalNotes    = NoteRepository.notes.size
    val totalFavorites = NoteRepository.favoriteNotes.size

    ProfileContent(
        totalNotes     = totalNotes,
        totalFavorites = totalFavorites
    )
}

/** STATELESS ProfileContent */
@Composable
fun ProfileContent(
    totalNotes: Int,
    totalFavorites: Int
) {
    Scaffold(
        topBar = {
            NotesTopBar(title = "Profil")
        }
    ) { innerPadding ->
        Column(
            modifier              = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.spacedBy(20.dp)
        ) {
            // ── Avatar placeholder ────────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFC795E6)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "👤",
                    style = MaterialTheme.typography.displaySmall
                )
            }

            // ── Nama & email ──────────────────────────────────────────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text       = "Mahasiswa ITERA",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text  = "mahasiswa@student.itera.ac.id",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // ── Statistik catatan ─────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = "Catatan",
                    value = totalNotes.toString()
                )
                StatItem(
                    label = "Favorit",
                    value = totalFavorites.toString()
                )
            }

            // Button "Edit profile info"
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C1EAE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Edit profile info", modifier = Modifier.padding(horizontal = 16.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Info aplikasi ─────────────────────────────────────────────
            Text(
                text       = "Tentang Aplikasi",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier   = Modifier.align(Alignment.Start)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(12.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier            = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoRow(label = "Mata Kuliah", value = "Pengembangan Aplikasi Mobile")
                    InfoRow(label = "Pertemuan",   value = "5 — Navigasi Antar Layar")
                    InfoRow(label = "Framework",   value = "Compose Multiplatform")
                    InfoRow(label = "Versi",       value = "1.0.0")
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text       = value,
            style      = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text  = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Satu baris info label: value */
@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text       = value,
            style      = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}