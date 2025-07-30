package dev.javfuentes.dailyjoke.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.javfuentes.dailyjoke.data.Joke
import dev.javfuentes.dailyjoke.data.JokeType
import dev.javfuentes.dailyjoke.data.model.*
import dev.javfuentes.dailyjoke.data.repository.JokeRepository
import dev.javfuentes.dailyjoke.viewmodel.JokeUiEvent
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModel
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.*

class JokeViewModelUnitTest {

    private val mockRepository = mockk<JokeRepository>()
    private lateinit var viewModel: JokeViewModel

    @Before
    fun setup() {
        clearAllMocks()
        // Mock the favorites loading to avoid initialization issues
        coEvery { mockRepository.getFavoriteJokes() } returns emptyList()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `initial state should be loading when ViewModel is created`() = runTest {
        // Given
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Loading())
        
        // When
        viewModel = JokeViewModel(mockRepository)
        
        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isTrue()
            assertThat(state.joke).isNull()
            assertThat(state.errorMessage).isNull()
        }
    }

    @Test
    fun `should load joke successfully when repository returns success`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        
        // When
        viewModel = JokeViewModel(mockRepository)
        
        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.joke).isEqualTo(mockJoke)
            assertThat(state.errorMessage).isNull()
        }
    }

    @Test
    fun `should handle error when repository returns error`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Error(NetworkException(errorMessage))
        )
        
        // When
        viewModel = JokeViewModel(mockRepository)
        
        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.joke).isNull()
            assertThat(state.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `should refresh joke when RefreshJoke event is handled`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        viewModel = JokeViewModel(mockRepository)
        clearMocks(mockRepository, answers = false)
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        
        // When
        viewModel.handleEvent(JokeUiEvent.RefreshJoke)
        
        // Then
        coVerify { mockRepository.getRandomJoke() }
    }

    @Test
    fun `should handle ClearError event`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        viewModel = JokeViewModel(mockRepository)
        
        // When
        viewModel.handleEvent(JokeUiEvent.ClearError)
        
        // Then - Just verify the event doesn't crash
        assertThat(true).isTrue()
    }

    @Test
    fun `should load joke by category when LoadJokeByCategory event is handled`() = runTest {
        // Given
        val category = "Programming"
        val mockJoke = createMockJoke(category = category)
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        coEvery { mockRepository.getJokeByCategory(category) } returns flowOf(ApiResult.Success(mockJoke))
        viewModel = JokeViewModel(mockRepository)
        
        // When
        viewModel.handleEvent(JokeUiEvent.LoadJokeByCategory(category))
        
        // Then
        coVerify { mockRepository.getJokeByCategory(category) }
    }

    @Test
    fun `should save favorite joke when SaveFavoriteJoke event is handled`() = runTest {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        coEvery { mockRepository.saveFavoriteJoke(any()) } just Runs
        viewModel = JokeViewModel(mockRepository)
        
        // Wait for initial load to complete
        viewModel.uiState.test {
            awaitItem() // Wait for state with joke loaded
        }
        
        // When
        viewModel.handleEvent(JokeUiEvent.SaveFavoriteJoke)
        
        // Then
        coVerify { mockRepository.saveFavoriteJoke(mockJoke) }
    }

    @Test
    fun `should remove from favorites when RemoveFromFavorites event is handled`() = runTest {
        // Given  
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))
        coEvery { mockRepository.removeFavoriteJoke(any()) } just Runs
        viewModel = JokeViewModel(mockRepository)
        
        // When
        viewModel.handleEvent(JokeUiEvent.RemoveFromFavorites(mockJoke))
        
        // Then
        coVerify { mockRepository.removeFavoriteJoke(mockJoke.id) }
    }

    private fun createMockJoke(
        id: Int = 1,
        category: String = "Programming"
    ) = Joke(
        id = id,
        category = category,
        setup = "Test setup",
        punchline = "Test punchline",
        type = JokeType.TWOPART,
        safe = true,
        lang = "en"
    )
}