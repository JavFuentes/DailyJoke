package dev.javfuentes.dailyjoke.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.javfuentes.dailyjoke.ui.components.ErrorMessage
import dev.javfuentes.dailyjoke.ui.components.JokeCard
import dev.javfuentes.dailyjoke.ui.components.LoadingIndicator
import dev.javfuentes.dailyjoke.viewmodel.JokeUiEvent
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    modifier: Modifier = Modifier,
    viewModel: JokeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                            viewModel.handleEvent(JokeUiEvent.ClearError)
                            viewModel.handleEvent(JokeUiEvent.LoadJoke)
                        }
                    )
                }
                uiState.joke != null -> {
                    JokeCard(joke = uiState.joke!!)
                }
            }
        }

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Refresh button
            Button(
                onClick = { viewModel.handleEvent(JokeUiEvent.RefreshJoke) },
                enabled = !uiState.isLoading && !uiState.isRefreshing,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("New Joke")
            }
        }
    }
}