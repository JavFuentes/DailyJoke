package dev.javfuentes.dailyjoke.network

import dev.javfuentes.dailyjoke.data.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getJoke(
        @Query("type") type: String = "twopart",
        @Query("safe-mode") safeMode: String = "true"
    ): Response<JokeResponse>
}