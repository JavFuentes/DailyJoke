package dev.javfuentes.dailyjoke.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.javfuentes.dailyjoke.di.AppModule
import dev.javfuentes.dailyjoke.ui.navigation.DailyJokeScreen
import dev.javfuentes.dailyjoke.ui.screens.FavoriteJokesScreen
import dev.javfuentes.dailyjoke.ui.screens.JokeScreen
import dev.javfuentes.dailyjoke.viewmodel.JokeUiEvent
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModel

@Composable
fun DailyJokeApp(
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(DailyJokeScreen.JOKE) }
    val context = LocalContext.current
    
    val viewModelFactory = AppModule.provideJokeViewModelFactory(context)
    val viewModel: JokeViewModel = viewModel { viewModelFactory.create(JokeViewModel::class.java) }
    val uiState by viewModel.uiState.collectAsState()

    when (currentScreen) {
        DailyJokeScreen.JOKE -> {
            JokeScreen(
                modifier = modifier,
                uiState = uiState,
                onEvent = viewModel::handleEvent,
                onNavigateToFavorites = {
                    currentScreen = DailyJokeScreen.FAVORITES
                }
            )
        }
        DailyJokeScreen.FAVORITES -> {
            FavoriteJokesScreen(
                modifier = modifier,
                uiState = uiState,
                onNavigateBack = {
                    currentScreen = DailyJokeScreen.JOKE
                },
                onRemoveFromFavorites = { joke ->
                    viewModel.handleEvent(JokeUiEvent.RemoveFromFavorites(joke))
                }
            )
        }
    }
}