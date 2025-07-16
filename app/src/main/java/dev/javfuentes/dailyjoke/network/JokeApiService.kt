package dev.javfuentes.dailyjoke.network

import dev.javfuentes.dailyjoke.data.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getRandomJoke(
        @Query("type") type: String = "twopart",
        @Query("safe-mode") safeMode: String = "true",
        @Query("lang") language: String = "en"
    ): Response<JokeResponse>
    
    @GET("joke/{category}")
    suspend fun getJokeByCategory(
        @Path("category") category: String,
        @Query("type") type: String = "twopart",
        @Query("safe-mode") safeMode: String = "true",
        @Query("lang") language: String = "en"
    ): Response<JokeResponse>
    
    @GET("joke/Any")
    suspend fun getJokeById(
        @Query("idRange") idRange: String
    ): Response<JokeResponse>
}