package dev.javfuentes.dailyjoke.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.javfuentes.dailyjoke.data.Joke
import dev.javfuentes.dailyjoke.data.JokeType
import dev.javfuentes.dailyjoke.data.model.*
import dev.javfuentes.dailyjoke.data.repository.JokeRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class JokeViewModelTest {

    private val mockRepository = mockk<JokeRepository>()
    private lateinit var viewModel: JokeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = JokeViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should be loading`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Loading(),
            ApiResult.Success(mockJoke)
        )

        // When
        viewModel.uiState.test {
            // Then
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.joke).isNull()
            assertThat(initialState.errorMessage).isNull()
        }
    }

    @Test
    fun `handleEvent LoadJoke should update state with success`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Loading(),
            ApiResult.Success(mockJoke)
        )

        // When
        viewModel.handleEvent(JokeUiEvent.LoadJoke)

        // Then
        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.joke).isEqualTo(mockJoke)
            assertThat(successState.errorMessage).isNull()
        }
    }

    @Test
    fun `handleEvent LoadJoke should update state with error`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Loading(),
            ApiResult.Error(NetworkException(errorMessage))
        )

        // When
        viewModel.handleEvent(JokeUiEvent.LoadJoke)

        // Then
        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.joke).isNull()
            assertThat(errorState.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `handleEvent RefreshJoke should set isRefreshing to true`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Loading(),
            ApiResult.Success(mockJoke)
        )

        // When
        viewModel.handleEvent(JokeUiEvent.RefreshJoke)

        // Then
        viewModel.uiState.test {
            val refreshingState = awaitItem()
            assertThat(refreshingState.isRefreshing).isTrue()
        }
    }

    @Test
    fun `handleEvent ClearError should clear error message`() = runTest {
        // Given
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Error(NetworkException("Error"))
        )
        viewModel.handleEvent(JokeUiEvent.LoadJoke)

        // When
        viewModel.handleEvent(JokeUiEvent.ClearError)

        // Then
        viewModel.uiState.test {
            val clearedState = awaitItem()
            assertThat(clearedState.errorMessage).isNull()
        }
    }

    @Test
    fun `handleEvent SaveFavoriteJoke should add current joke to favorites list`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Success(mockJoke)
        )
        
        // Load a joke first
        viewModel.handleEvent(JokeUiEvent.LoadJoke)
        
        // When
        viewModel.handleEvent(JokeUiEvent.SaveFavoriteJoke)
        
        // Then
        viewModel.uiState.test {
            val stateWithFavorite = awaitItem()
            assertThat(stateWithFavorite.favoriteJokes).hasSize(1)
            assertThat(stateWithFavorite.favoriteJokes).contains(mockJoke)
        }
    }

    @Test
    fun `handleEvent SaveFavoriteJoke should not add duplicate jokes to favorites`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Success(mockJoke)
        )
        
        // Load a joke first
        viewModel.handleEvent(JokeUiEvent.LoadJoke)
        
        // When - save the same joke twice
        viewModel.handleEvent(JokeUiEvent.SaveFavoriteJoke)
        viewModel.handleEvent(JokeUiEvent.SaveFavoriteJoke)
        
        // Then
        viewModel.uiState.test {
            val stateWithFavorites = awaitItem()
            assertThat(stateWithFavorites.favoriteJokes).hasSize(1)
            assertThat(stateWithFavorites.favoriteJokes).contains(mockJoke)
        }
    }

    @Test
    fun `handleEvent SaveFavoriteJoke should do nothing when no current joke exists`() = runTest {
        // Given - no joke loaded (initial state)
        
        // When
        viewModel.handleEvent(JokeUiEvent.SaveFavoriteJoke)
        
        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.favoriteJokes).isEmpty()
            assertThat(state.joke).isNull()
        }
    }

    private fun createMockJoke() = Joke(
        id = 1,
        category = "Programming",
        setup = "Why do programmers prefer dark mode?",
        punchline = "Because light attracts bugs!",
        type = JokeType.TWOPART,
        safe = true,
        lang = "en"
    )
}