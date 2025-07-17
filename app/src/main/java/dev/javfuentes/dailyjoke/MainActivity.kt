package dev.javfuentes.dailyjoke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.javfuentes.dailyjoke.ui.DailyJokeApp
import dev.javfuentes.dailyjoke.ui.theme.DailyJokeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            DailyJokeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ) { innerPadding ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = innerPadding.calculateTopPadding() + 72.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = innerPadding.calculateBottomPadding() + 72.dp
                            ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp,
                            pressedElevation = 8.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        DailyJokeApp(
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
    }
}

