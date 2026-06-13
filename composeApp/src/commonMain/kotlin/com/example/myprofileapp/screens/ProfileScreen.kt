package com.example.myprofileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.ProfileUiState
import com.example.myprofileapp.viewmodel.NotesViewModel
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.profile_refi
import org.jetbrains.compose.resources.painterResource

val CreamBackground = Color(0xFFFAF5E9)
val CardBackground = Color(0xFFF2ECE0)
val GreenBanner = Color(0xFFA5B872)
val DarkText = Color(0xFF2C2C2C)
val LightText = Color(0xFF7A7A7A)
val IconYellow = Color(0xFFFFD54F)
val IconPink = Color(0xFFF48FB1)
val IconGreen = Color(0xFFAED581)

@Composable
fun ProfileScreen(
    uiState      : ProfileUiState,
    notesViewModel: NotesViewModel,
    onEditClick  : () -> Unit,
    onToggleDark : () -> Unit,
    onSaveContact: (String, String) -> Unit,
    onMenuClick  : () -> Unit = {}
) {
    var editContactDialog by remember { mutableStateOf<Pair<String, String>?>(null) }
    
    val notes by notesViewModel.notes.collectAsState()
    val totalNotes    = notes.size
    val totalFavorites = notes.count { it.isFavorite }

    Scaffold(
        topBar = {
            NotesTopBar(
                title = "Profil",
                onMenuClick = onMenuClick,
                actions = {
                    Text(
                        text = if (uiState.isDarkMode) "☀️" else "🌙",
                        modifier = Modifier
                            .clickable { onToggleDark() }
                            .padding(8.dp),
                        fontSize = 20.sp
                    )
                }
            )
        },
        containerColor = if (uiState.isDarkMode) MaterialTheme.colorScheme.background else CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(24.dp))

            // ── Header Section ────────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar with Edit Badge
                Box {
                    Image(
                        painter = painterResource(Res.drawable.profile_refi),
                        contentDescription = "Profile Photo",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )
                    // Edit Badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 8.dp, y = (-8).dp)
                            .size(32.dp)
                            .background(Color(0xFFF48FB1), CircleShape)
                            .clickable { onEditClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(Modifier.width(24.dp))

                // Name and Role
                Column {
                    val names = uiState.name.split(" ", limit = 2)
                    val firstName = names.getOrNull(0) ?: ""
                    val lastName = names.getOrNull(1) ?: ""
                    
                    Text(
                        text = firstName,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isDarkMode) Color.White else DarkText,
                        lineHeight = 36.sp
                    )
                    if (lastName.isNotEmpty()) {
                        Text(
                            text = lastName,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.isDarkMode) Color.White else DarkText,
                            lineHeight = 36.sp
                        )
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Text(
                        text = uiState.role.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isDarkMode) Color.LightGray else LightText,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Green Banner for Bio / Status ─────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(GreenBanner)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Text(
                    text = uiState.bio.takeIf { it.isNotBlank() } ?: "No bio provided.",
                    color = DarkText, // Tetap gelap karena background banner hijau
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 3
                )
            }

            Spacer(Modifier.height(32.dp))

            // ── Contact Info List ─────────────────────────────────────────────────────
            Text(
                text = "Contact profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (uiState.isDarkMode) Color.White else DarkText
            )
            
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (uiState.isDarkMode) MaterialTheme.colorScheme.surfaceVariant else CardBackground)
                    .padding(vertical = 8.dp)
            ) {
                ContactListItem(
                    icon = Icons.Rounded.Email,
                    iconBgColor = IconYellow,
                    label = "Email",
                    value = uiState.email,
                    isDarkMode = uiState.isDarkMode,
                    onClick = { editContactDialog = "Email" to uiState.email }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                ContactListItem(
                    icon = Icons.Rounded.Phone,
                    iconBgColor = IconPink,
                    label = "Phone",
                    value = uiState.phone,
                    isDarkMode = uiState.isDarkMode,
                    onClick = { editContactDialog = "Phone" to uiState.phone }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                ContactListItem(
                    icon = Icons.Rounded.LocationOn,
                    iconBgColor = IconGreen,
                    label = "Location",
                    value = uiState.location,
                    isDarkMode = uiState.isDarkMode,
                    onClick = { editContactDialog = "Location" to uiState.location }
                )
            }
            
            Spacer(Modifier.height(32.dp))

            // ── Statistik catatan ─────────────────────────────────────────────────────
            Text(
                text       = "Statistik",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = if (uiState.isDarkMode) Color.White else DarkText
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
                    isDarkMode = uiState.isDarkMode,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label    = "Favorit",
                    value    = totalFavorites.toString(),
                    emoji    = "❤️",
                    isDarkMode = uiState.isDarkMode,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(32.dp))

            // ── Info aplikasi ─────────────────────────────────────────────────────────
            Text(
                text       = "Tentang Aplikasi",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = if (uiState.isDarkMode) Color.White else DarkText
            )
            
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (uiState.isDarkMode) MaterialTheme.colorScheme.surfaceVariant else CardBackground)
                    .padding(vertical = 8.dp)
            ) {
                InfoRow(label = "Mata Kuliah", value = "Pengembangan Aplikasi Mobile", isDarkMode = uiState.isDarkMode)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                InfoRow(label = "Pertemuan",   value = "7 — Local Data Storage", isDarkMode = uiState.isDarkMode)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                InfoRow(label = "Framework",   value = "Compose Multiplatform", isDarkMode = uiState.isDarkMode)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = LightText.copy(alpha = 0.2f))
                InfoRow(label = "Versi",       value = "1.0.0", isDarkMode = uiState.isDarkMode)
            }
            
            Spacer(Modifier.height(32.dp))
        }

        // Dialog Pop-up
        editContactDialog?.let { (label, value) ->
            AlertDialog(
                onDismissRequest = { editContactDialog = null },
                title = { 
                    Text(
                        text = "$label Details", 
                        color = if (uiState.isDarkMode) Color.White else DarkText,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                text = {
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        color = if (uiState.isDarkMode) Color.LightGray else DarkText
                    )
                },
                confirmButton = {
                    TextButton(onClick = { editContactDialog = null }) {
                        Text("Close", fontWeight = FontWeight.Bold, color = GreenBanner)
                    }
                },
                containerColor = if (uiState.isDarkMode) Color(0xFF2C2C2C) else CreamBackground
            )
        }
    }
}

@Composable
private fun ContactListItem(
    icon: ImageVector,
    iconBgColor: Color,
    label: String,
    value: String,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = DarkText,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) Color.White else DarkText
            )
            if (value.isNotBlank()) {
                Text(
                    text = value,
                    fontSize = 12.sp,
                    color = if (isDarkMode) Color.LightGray else LightText,
                    maxLines = 1
                )
            }
        }
        
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = "Detail",
            tint = if (isDarkMode) Color.LightGray else LightText
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    emoji: String,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isDarkMode) MaterialTheme.colorScheme.surfaceVariant else CardBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Text(
            text       = value,
            fontSize   = 32.sp,
            fontWeight = FontWeight.Bold,
            color      = if (isDarkMode) Color.White else DarkText
        )
        Text(
            text  = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isDarkMode) Color.LightGray else LightText
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String, isDarkMode: Boolean) {
    Row(
        modifier              = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text  = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.White else DarkText
        )
        Text(
            text       = value,
            fontSize   = 12.sp,
            color      = if (isDarkMode) Color.LightGray else LightText,
            fontWeight = FontWeight.Medium
        )
    }
}