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

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    article: Article,
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            article.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title ?: "Tanpa Judul",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (!article.author.isNullOrBlank() || !article.publishedAt.isNullOrBlank()) {
                    Text(
                        text = buildString {
                            if (!article.author.isNullOrBlank()) append("Oleh ${article.author}")
                            if (!article.author.isNullOrBlank() && !article.publishedAt.isNullOrBlank()) append(" • ")
                            if (!article.publishedAt.isNullOrBlank()) {
                                // Simple date formatting hack for ISO-8601 (e.g. 2022-04-21T09:49:33Z -> 2022-04-21)
                                append(article.publishedAt.take(10)) 
                            }
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = article.content ?: article.description ?: "Konten berita tidak tersedia.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Tautan Asli:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.url ?: "-",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
