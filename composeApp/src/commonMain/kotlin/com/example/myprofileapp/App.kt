package com.example.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofileapp.ui.EditProfileScreen
import com.example.myprofileapp.ui.ProfileScreen
import com.example.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun App(profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }) {

    val uiState by profileViewModel.uiState.collectAsState()

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (uiState.isEditMode) {
                EditProfileScreen(
                    currentName = uiState.name,
                    currentBio  = uiState.bio,
                    onSave      = { name, bio -> profileViewModel.saveProfile(name, bio) },
                    onCancel    = { profileViewModel.closeEditMode() }
                )
            } else {
                ProfileScreen(
                    uiState      = uiState,
                    onEditClick  = { profileViewModel.openEditMode() },
                    onToggleDark = { profileViewModel.toggleDarkMode() }
                )
            }
        }
    }
}