package com.example.myprofileapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.Article
import com.example.myprofileapp.viewmodel.NewsUiState
import com.example.myprofileapp.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    viewModel: NewsViewModel,
    onArticleClick: (String) -> Unit, // pass URL as ID or just string
    onMenuClick: () -> Unit = {},
    isDarkMode: Boolean,
    onToggleDark: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            NotesTopBar(
                title = "Berita Terkini",
                onMenuClick = onMenuClick,
                actions = {
                    IconButton(onClick = onToggleDark) {
                        Text(if (isDarkMode) "☀️" else "🌙", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refreshNews() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is NewsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is NewsUiState.Success -> {
                    val articles = (uiState as NewsUiState.Success).articles
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(articles) { article ->
                            NewsCard(
                                article = article,
                                onClick = {
                                    // For simplicity, pass URL encoded or handle detail differently
                                    article.url?.let { onArticleClick(it) }
                                }
                            )
                        }
                    }
                }
                is NewsUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = (uiState as NewsUiState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchNews() }) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column {
            article.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title ?: "Tanpa Judul",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.description ?: "Tidak ada deskripsi",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
