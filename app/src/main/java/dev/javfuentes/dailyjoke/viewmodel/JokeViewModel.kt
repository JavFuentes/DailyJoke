package dev.javfuentes.dailyjoke.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.javfuentes.dailyjoke.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class JokeUiState(
    val setup: String = "",
    val punchline: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class JokeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(JokeUiState())
    val uiState: StateFlow<JokeUiState> = _uiState.asStateFlow()
    
    init {
        fetchJoke()
    }
    
    fun fetchJoke() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val response = RetrofitInstance.api.getJoke()
                if (response.isSuccessful) {
                    val jokeResponse = response.body()
                    if (jokeResponse != null && !jokeResponse.error) {
                        _uiState.value = _uiState.value.copy(
                            setup = jokeResponse.setup ?: "",
                            punchline = jokeResponse.delivery ?: "",
                            isLoading = false,
                            error = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = jokeResponse?.message ?: "Error fetching joke"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Network error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }
}