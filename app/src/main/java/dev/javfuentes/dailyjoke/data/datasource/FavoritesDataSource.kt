package dev.javfuentes.dailyjoke.data.datasource

import dev.javfuentes.dailyjoke.data.Joke

interface FavoritesDataSource {
    suspend fun saveFavoriteJoke(joke: Joke)
    suspend fun removeFavoriteJoke(jokeId: Int)
    suspend fun getFavoriteJokes(): List<Joke>
    suspend fun isFavorite(jokeId: Int): Boolean
}