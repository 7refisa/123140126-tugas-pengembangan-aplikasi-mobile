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

            // ── Info aplikasi (Colorful Grid) ─────────────────────────────
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text       = "Tentang Aplikasi",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ColoredAppInfoCard(
                        title = "Mata Kuliah",
                        value = "PAM",
                        color = Color(0xFF98D190), // Green 1
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                    )
                    ColoredAppInfoCard(
                        title = "Pertemuan",
                        value = "5",
                        color = Color(0xFF8BE18B), // Green 2
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ColoredAppInfoCard(
                        title = "Framework",
                        value = "Compose",
                        color = Color(0xFFECA343), // Orange
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                    )
                    ColoredAppInfoCard(
                        title = "Versi",
                        value = "1.0.0",
                        color = Color(0xFF29225E), // Dark Blue
                        textColor = Color.White,
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                    )
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

@Composable
private fun ColoredAppInfoCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor.copy(alpha = 0.7f)
            )
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}