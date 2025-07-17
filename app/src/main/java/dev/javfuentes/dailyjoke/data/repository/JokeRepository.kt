package dev.javfuentes.dailyjoke.data.repository

import dev.javfuentes.dailyjoke.data.Joke
import dev.javfuentes.dailyjoke.data.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface JokeRepository {
    suspend fun getRandomJoke(): Flow<ApiResult<Joke>>
    suspend fun getJokeByCategory(category: String): Flow<ApiResult<Joke>>
    suspend fun getJokeById(id: Int): Flow<ApiResult<Joke>>
    
    // Favorites methods
    suspend fun saveFavoriteJoke(joke: Joke)
    suspend fun removeFavoriteJoke(jokeId: Int)
    suspend fun getFavoriteJokes(): List<Joke>
    suspend fun isFavorite(jokeId: Int): Boolean
}