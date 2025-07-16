package dev.javfuentes.dailyjoke.data.model

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    data class Loading(val isLoading: Boolean = true) : ApiResult<Nothing>()
}

data class ApiError(
    val message: String,
    val code: Int? = null,
    val details: List<String>? = null
)

class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause)
class ApiException(val error: ApiError) : Exception(error.message)