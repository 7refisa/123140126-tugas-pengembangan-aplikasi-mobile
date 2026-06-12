package com.example.myprofileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.ProfileUiState

@Composable
fun EditProfileScreen(
    uiState     : ProfileUiState,
    onSave      : (String, String, String, String, String) -> Unit,
    onCancel    : () -> Unit
) {
    var nameInput     by remember { mutableStateOf(uiState.name) }
    var bioInput      by remember { mutableStateOf(uiState.bio) }
    var emailInput    by remember { mutableStateOf(uiState.email) }
    var phoneInput    by remember { mutableStateOf(uiState.phone) }
    var locationInput by remember { mutableStateOf(uiState.location) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (uiState.isDarkMode) MaterialTheme.colorScheme.background else CreamBackground)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))
            
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onCancel) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Kembali",
                        tint = if (uiState.isDarkMode) Color.White else DarkText
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Edit Profile",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (uiState.isDarkMode) Color.White else DarkText
                )
            }

            Spacer(Modifier.height(32.dp))

            // TextFields
            EditTextField(
                label = "Name",
                value = nameInput,
                onValueChange = { nameInput = it },
                isDarkMode = uiState.isDarkMode
            )
            Spacer(Modifier.height(16.dp))
            EditTextField(
                label = "Bio",
                value = bioInput,
                onValueChange = { bioInput = it },
                singleLine = false,
                minLines = 3,
                isDarkMode = uiState.isDarkMode
            )
            Spacer(Modifier.height(16.dp))
            EditTextField(
                label = "Email",
                value = emailInput,
                onValueChange = { emailInput = it },
                isDarkMode = uiState.isDarkMode
            )
            Spacer(Modifier.height(16.dp))
            EditTextField(
                label = "Phone",
                value = phoneInput,
                onValueChange = { phoneInput = it },
                isDarkMode = uiState.isDarkMode
            )
            Spacer(Modifier.height(16.dp))
            EditTextField(
                label = "Location",
                value = locationInput,
                onValueChange = { locationInput = it },
                isDarkMode = uiState.isDarkMode
            )

            Spacer(Modifier.height(40.dp))

            // Save Button
            Button(
                onClick = { onSave(nameInput, bioInput, emailInput, phoneInput, locationInput) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenBanner),
                enabled = nameInput.isNotBlank()
            ) {
                Text("Save", color = DarkText, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            // Cancel Button
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = if (uiState.isDarkMode) Color.White else DarkText)
            ) {
                Text("Cancel", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun EditTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    minLines: Int = 1,
    isDarkMode: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = if (isDarkMode) Color.LightGray else LightText) },
        singleLine = singleLine,
        minLines = minLines,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = LightText.copy(alpha = 0.3f),
            focusedBorderColor = GreenBanner,
            unfocusedTextColor = if (isDarkMode) Color.White else DarkText,
            focusedTextColor = if (isDarkMode) Color.White else DarkText
        )
    )
}
