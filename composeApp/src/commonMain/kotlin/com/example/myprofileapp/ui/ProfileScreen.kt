package com.example.myprofileapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.ProfileUiState
import com.example.myprofileapp.InfoItem
import com.example.myprofileapp.ProfileHeader
import com.example.myprofileapp.BioSection

@Composable
fun ProfileScreen(
    uiState      : ProfileUiState,
    onEditClick  : () -> Unit,
    onToggleDark : () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Konten utama
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            ProfileHeader(
                name = uiState.name,
                role = uiState.role
            )

            Spacer(Modifier.height(20.dp))

            BioSection(bio = uiState.bio)

            Spacer(Modifier.height(20.dp))

            OutlinedButton(
                onClick  = onEditClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector        = Icons.Rounded.Edit,
                    contentDescription = null,
                    modifier           = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Edit Profil")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick  = { showDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contact Me")
            }
        }

        // Toggle
        Switch(
            checked         = uiState.isDarkMode,
            onCheckedChange = { onToggleDark() },
            thumbContent    = {
                Text(
                    text     = if (uiState.isDarkMode) "🌙" else "☀️",
                    fontSize = 12.sp
                )
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        )
    }

    // Dialog kontak
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title            = { Text("Contact Info") },
            text             = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    InfoItem(icon = Icons.Rounded.Email,      label = "Email",    value = uiState.email)
                    InfoItem(icon = Icons.Rounded.Phone,      label = "Phone",    value = uiState.phone)
                    InfoItem(icon = Icons.Rounded.LocationOn, label = "Location", value = uiState.location)
                }
            },
            confirmButton    = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Tutup")
                }
            }
        )
    }
}