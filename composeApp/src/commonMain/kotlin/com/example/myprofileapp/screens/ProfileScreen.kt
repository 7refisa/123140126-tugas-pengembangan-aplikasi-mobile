package com.example.myprofileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.NoteRepository
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.profile_refi
import org.jetbrains.compose.resources.painterResource

val CreamBackground = Color(0xFFFAF5E9)
val CardBackground = Color(0xFFF2ECE0)
val DarkText = Color(0xFF2C2C2C)
val LightText = Color(0xFF7A7A7A)

// ─────────────────────────────────────────────────────────────────────────────
// ProfileScreen — Tab "Profile" di Bottom Navigation
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ProfileScreen() {
    val totalNotes    = NoteRepository.notes.size
    val totalFavorites = NoteRepository.favoriteNotes.size

    ProfileContent(
        totalNotes     = totalNotes,
        totalFavorites = totalFavorites
    )
}

@Composable
fun ProfileContent(
    totalNotes: Int,
    totalFavorites: Int
) {
    Scaffold(
        topBar = {
            NotesTopBar(title = "Profil")
        },
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // ── Header Profil (Kiri-Rata) ──────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Image(
                    painter = painterResource(Res.drawable.profile_refi),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(24.dp))

                // Nama & Email
                Column {
                    Text(
                        text = "Refi",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        lineHeight = 36.sp
                    )
                    Text(
                        text = "Ikhsanti",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        lineHeight = 36.sp
                    )
                    
                    Spacer(Modifier.height(8.dp))
                    
                    Text(
                        text = "refi.123140126@student.itera.ac.id",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightText,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── Statistik catatan ─────────────────────────────────────────
            Text(
                text       = "Statistik",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = DarkText,
                modifier   = Modifier.align(Alignment.Start)
            )
            
            Spacer(Modifier.height(16.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label    = "Total Catatan",
                    value    = totalNotes.toString(),
                    emoji    = "📝",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label    = "Favorit",
                    value    = totalFavorites.toString(),
                    emoji    = "❤️",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(32.dp))

            // ── Info aplikasi ─────────────────────────────────────────────
            Text(
                text       = "Tentang Aplikasi",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = DarkText,
                modifier   = Modifier.align(Alignment.Start)
            )
            
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(CardBackground)
                    .padding(vertical = 8.dp)
            ) {
                InfoRow(label = "Mata Kuliah", value = "Pengembangan Aplikasi Mobile")
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                InfoRow(label = "Pertemuan",   value = "5 — Navigasi Antar Layar")
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                InfoRow(label = "Framework",   value = "Compose Multiplatform")
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                InfoRow(label = "Versi",       value = "1.0.0")
            }
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    emoji: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Text(
            text       = value,
            fontSize   = 32.sp,
            fontWeight = FontWeight.Bold,
            color      = DarkText
        )
        Text(
            text  = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = LightText
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text  = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
        Text(
            text       = value,
            fontSize   = 12.sp,
            color      = LightText,
            fontWeight = FontWeight.Medium
        )
    }
}