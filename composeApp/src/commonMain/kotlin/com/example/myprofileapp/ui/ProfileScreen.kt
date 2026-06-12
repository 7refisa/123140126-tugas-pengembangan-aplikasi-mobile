package com.example.myprofileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.ProfileUiState
import com.example.myprofileapp.ProfileHeader
import com.example.myprofileapp.BioSection

@Composable
fun ProfileScreen(
    uiState      : ProfileUiState,
    onEditClick  : () -> Unit,
    onToggleDark : () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with purple background curve (simplified as a box)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFC795E6).copy(alpha = 0.2f))
                    .padding(top = 40.dp, bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                ProfileHeader(
                    name = uiState.name,
                    role = uiState.role
                )
            }

            Spacer(Modifier.height(16.dp))

            // Bio Section
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                BioSection(bio = uiState.bio)
            }

            Spacer(Modifier.height(24.dp))

            // Edit Profile Button
            Button(
                onClick = onEditClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C1EAE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector        = Icons.Rounded.Edit,
                    contentDescription = null,
                    modifier           = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Edit profile info", modifier = Modifier.padding(horizontal = 16.dp))
            }

            Spacer(Modifier.height(24.dp))

            // Contact Info as Colorful Grid Cards
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text       = "Contact Info",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ColoredAppInfoCard(
                        title = "Email",
                        value = uiState.email,
                        color = Color(0xFF98D190), // Green 1
                        modifier = Modifier.weight(1f).aspectRatio(1.2f)
                    )
                    ColoredAppInfoCard(
                        title = "Phone",
                        value = uiState.phone,
                        color = Color(0xFF8BE18B), // Green 2
                        modifier = Modifier.weight(1f).aspectRatio(1.2f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ColoredAppInfoCard(
                        title = "Location",
                        value = uiState.location,
                        color = Color(0xFFECA343), // Orange
                        modifier = Modifier.weight(1f).aspectRatio(1.2f)
                    )
                    // Empty box to maintain grid alignment
                    Box(modifier = Modifier.weight(1f))
                }
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
                style = MaterialTheme.typography.bodySmall,
                color = textColor.copy(alpha = 0.7f)
            )
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}