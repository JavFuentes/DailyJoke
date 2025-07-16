package dev.javfuentes.dailyjoke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.javfuentes.dailyjoke.data.repository.JokeRepositoryImpl
import dev.javfuentes.dailyjoke.network.NetworkModule
import dev.javfuentes.dailyjoke.ui.screens.JokeScreen
import dev.javfuentes.dailyjoke.ui.theme.DailyJokeTheme
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModel
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val repository = JokeRepositoryImpl(NetworkModule.jokeApiService)
        val viewModelFactory = JokeViewModelFactory(repository)
        
        setContent {
            DailyJokeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JokeScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel(factory = viewModelFactory)
                    )
                }
            }
        }
    }
}

