package dev.javfuentes.dailyjoke.data.datasource

import android.content.Context
import android.content.SharedPreferences
import dev.javfuentes.dailyjoke.data.Joke
import dev.javfuentes.dailyjoke.data.JokeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class FavoritesDataSourceImpl(
    private val context: Context
) : FavoritesDataSource {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override suspend fun saveFavoriteJoke(joke: Joke) = withContext(Dispatchers.IO) {
        val favorites = getFavoriteJokes().toMutableList()
        
        // Check if joke is already in favorites
        if (!favorites.any { it.id == joke.id }) {
            favorites.add(joke)
            saveFavoritesToPrefs(favorites)
        }
    }

    override suspend fun removeFavoriteJoke(jokeId: Int) = withContext(Dispatchers.IO) {
        val favorites = getFavoriteJokes().toMutableList()
        favorites.removeAll { it.id == jokeId }
        saveFavoritesToPrefs(favorites)
    }

    override suspend fun getFavoriteJokes(): List<Joke> = withContext(Dispatchers.IO) {
        val favoritesJson = sharedPreferences.getString(KEY_FAVORITES, "[]") ?: "[]"
        parseJokesFromJson(favoritesJson)
    }

    override suspend fun isFavorite(jokeId: Int): Boolean = withContext(Dispatchers.IO) {
        getFavoriteJokes().any { it.id == jokeId }
    }

    private fun saveFavoritesToPrefs(favorites: List<Joke>) {
        val jsonArray = JSONArray()
        
        favorites.forEach { joke ->
            val jokeJson = JSONObject().apply {
                put("id", joke.id)
                put("category", joke.category)
                put("setup", joke.setup)
                put("punchline", joke.punchline)
                put("type", joke.type.name)
                put("safe", joke.safe)
                put("lang", joke.lang)
            }
            jsonArray.put(jokeJson)
        }
        
        sharedPreferences.edit()
            .putString(KEY_FAVORITES, jsonArray.toString())
            .apply()
    }

    private fun parseJokesFromJson(jsonString: String): List<Joke> {
        return try {
            val jsonArray = JSONArray(jsonString)
            val jokes = mutableListOf<Joke>()
            
            for (i in 0 until jsonArray.length()) {
                val jokeJson = jsonArray.getJSONObject(i)
                val joke = Joke(
                    id = jokeJson.getInt("id"),
                    category = jokeJson.getString("category"),
                    setup = jokeJson.getString("setup"),
                    punchline = jokeJson.getString("punchline"),
                    type = JokeType.valueOf(jokeJson.getString("type")),
                    safe = jokeJson.getBoolean("safe"),
                    lang = jokeJson.getString("lang")
                )
                jokes.add(joke)
            }
            
            jokes
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val PREFS_NAME = "daily_joke_favorites"
        private const val KEY_FAVORITES = "favorite_jokes"
    }
}