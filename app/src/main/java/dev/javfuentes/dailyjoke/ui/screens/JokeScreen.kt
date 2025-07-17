package dev.javfuentes.dailyjoke.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.javfuentes.dailyjoke.di.AppModule
import dev.javfuentes.dailyjoke.ui.components.ErrorMessage
import dev.javfuentes.dailyjoke.ui.components.JokeCard
import dev.javfuentes.dailyjoke.ui.components.LoadingIndicator
import dev.javfuentes.dailyjoke.viewmodel.JokeUiEvent
import dev.javfuentes.dailyjoke.viewmodel.JokeUiState
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModelFactory = AppModule.provideJokeViewModelFactory(context)
    
    val viewModel: JokeViewModel = viewModel { viewModelFactory.create(JokeViewModel::class.java) }
    
    JokeScreen(
        modifier = modifier,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    modifier: Modifier = Modifier,
    viewModel: JokeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    
    JokeScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::handleEvent,
        onNavigateToFavorites = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    modifier: Modifier = Modifier,
    uiState: JokeUiState,
    onEvent: (JokeUiEvent) -> Unit,
    onNavigateToFavorites: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Text(
            text = "Daily Joke",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Content
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }
                uiState.errorMessage != null -> {
                    ErrorMessage(
                        message = uiState.errorMessage!!,
                        onRetry = {
                            onEvent(JokeUiEvent.ClearError)
                            onEvent(JokeUiEvent.LoadJoke)
                        }
                    )
                }
                uiState.joke != null -> {
                    JokeCard(joke = uiState.joke!!)
                }
            }
        }

        // Action buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top row: Save to favorites and View favorites
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Save to favorites button
                Button(
                    onClick = { onEvent(JokeUiEvent.SaveFavoriteJoke) },
                    enabled = !uiState.isLoading && !uiState.isRefreshing && uiState.joke != null,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    val isCurrentJokeFavorite = uiState.joke?.let { currentJoke ->
                        uiState.favoriteJokes.contains(currentJoke)
                    } ?: false
                    
                    Icon(
                        imageVector = if (isCurrentJokeFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isCurrentJokeFavorite) "Saved" else "Save")
                }

                // View favorites button
                OutlinedButton(
                    onClick = onNavigateToFavorites,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Favorites")
                }
            }
            
            // Bottom row: New joke button
            Button(
                onClick = { onEvent(JokeUiEvent.RefreshJoke) },
                enabled = !uiState.isLoading && !uiState.isRefreshing,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("New Joke")
            }
        }
    }
}