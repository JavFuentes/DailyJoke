package dev.javfuentes.dailyjoke.data.repository

import dev.javfuentes.dailyjoke.data.Joke
import dev.javfuentes.dailyjoke.data.JokeResponse
import dev.javfuentes.dailyjoke.data.JokeType
import dev.javfuentes.dailyjoke.data.model.ApiError
import dev.javfuentes.dailyjoke.data.model.ApiException
import dev.javfuentes.dailyjoke.data.model.ApiResult
import dev.javfuentes.dailyjoke.data.model.NetworkException
import dev.javfuentes.dailyjoke.network.JokeApiService
import dev.javfuentes.dailyjoke.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

class JokeRepositoryImpl(
    private val apiService: JokeApiService
) : JokeRepository {

    override suspend fun getRandomJoke(): Flow<ApiResult<Joke>> = flow {
        emit(ApiResult.Loading())
        
        try {
            val response = apiService.getRandomJoke(
                type = Constants.JOKE_TYPE_TWOPART,
                safeMode = Constants.SAFE_MODE,
                language = Constants.DEFAULT_LANGUAGE
            )
            
            emit(handleApiResponse(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(NetworkException("Network error. Please check your connection.", e)))
        } catch (e: Exception) {
            emit(ApiResult.Error(e))
        }
    }

    override suspend fun getJokeByCategory(category: String): Flow<ApiResult<Joke>> = flow {
        emit(ApiResult.Loading())
        
        try {
            val response = apiService.getJokeByCategory(
                category = category,
                type = Constants.JOKE_TYPE_TWOPART,
                safeMode = Constants.SAFE_MODE,
                language = Constants.DEFAULT_LANGUAGE
            )
            
            emit(handleApiResponse(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(NetworkException("Network error. Please check your connection.", e)))
        } catch (e: Exception) {
            emit(ApiResult.Error(e))
        }
    }

    override suspend fun getJokeById(id: Int): Flow<ApiResult<Joke>> = flow {
        emit(ApiResult.Loading())
        
        try {
            val response = apiService.getJokeById(idRange = id.toString())
            emit(handleApiResponse(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(NetworkException("Network error. Please check your connection.", e)))
        } catch (e: Exception) {
            emit(ApiResult.Error(e))
        }
    }

    private fun handleApiResponse(response: Response<JokeResponse>): ApiResult<Joke> {
        return if (response.isSuccessful) {
            val jokeResponse = response.body()
            if (jokeResponse != null && !jokeResponse.error) {
                val joke = mapToJoke(jokeResponse)
                ApiResult.Success(joke)
            } else {
                val errorMessage = jokeResponse?.message ?: "Unknown error occurred"
                val details = jokeResponse?.causedBy
                ApiResult.Error(ApiException(ApiError(errorMessage, details = details)))
            }
        } else {
            ApiResult.Error(NetworkException("HTTP ${response.code()}: ${response.message()}"))
        }
    }

    private fun mapToJoke(response: JokeResponse): Joke {
        return when (response.type) {
            "twopart" -> Joke(
                id = response.id ?: 0,
                category = response.category ?: "Unknown",
                setup = response.setup ?: "",
                punchline = response.delivery ?: "",
                type = JokeType.TWOPART,
                safe = response.safe ?: true,
                lang = response.lang ?: "en"
            )
            "single" -> Joke(
                id = response.id ?: 0,
                category = response.category ?: "Unknown",
                setup = response.joke ?: "",
                punchline = "", // Single jokes don't have punchlines
                type = JokeType.SINGLE,
                safe = response.safe ?: true,
                lang = response.lang ?: "en"
            )
            else -> throw IllegalArgumentException("Unknown joke type: ${response.type}")
        }
    }
}