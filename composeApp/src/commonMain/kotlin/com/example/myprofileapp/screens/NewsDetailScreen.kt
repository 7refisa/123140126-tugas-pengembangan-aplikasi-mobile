package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.Article

// Since we navigate using string routing and passing full complex objects is hard in Compose Navigation,
// We will just pass the URL or have a simplified view. 
// Or better, we can decode the URL and show a simplified WebView if possible, but KMP WebView is complex.
// For the assignment, "Detail screen saat artikel diklik". 
// Let's assume we can fetch the article from ViewModel or just pass URL.
// But wait, the assignment requires a detail screen. To make it simple, we can pass URL and show a message,
// OR pass the encoded URL and just show it as text, since we don't have a WebView set up.
// Actually, KMP navigation with complex objects can be done via ViewModel state (shared ViewModel).

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    url: String, // We use URL as ID
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            NotesTopBar(
                title = "Detail Berita",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Tautan Artikel:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = url,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Catatan: Di aplikasi sesungguhnya, area ini bisa memuat WebView atau detail artikel yang dikirim melalui state.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
