package dev.javfuentes.dailyjoke.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.javfuentes.dailyjoke.data.JokeResponse
import dev.javfuentes.dailyjoke.data.datasource.FavoritesDataSource
import dev.javfuentes.dailyjoke.data.model.*
import dev.javfuentes.dailyjoke.network.JokeApiService
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.*
import retrofit2.Response
import java.io.IOException

class JokeRepositoryTest {

    private val mockApiService = mockk<JokeApiService>()
    private val mockFavoritesDataSource = mockk<FavoritesDataSource>()
    private lateinit var repository: JokeRepository

    @Before
    fun setup() {
        repository = JokeRepositoryImpl(mockApiService, mockFavoritesDataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getRandomJoke should return success when API call succeeds`() = runTest {
        // Given
        val mockResponse = createMockJokeResponse()
        coEvery { mockApiService.getRandomJoke(any(), any(), any()) } returns 
            Response.success(mockResponse)

        // When
        repository.getRandomJoke().test {
            // Then
            val loadingResult = awaitItem()
            assertThat(loadingResult).isInstanceOf(ApiResult.Loading::class.java)

            val successResult = awaitItem()
            assertThat(successResult).isInstanceOf(ApiResult.Success::class.java)
            val joke = (successResult as ApiResult.Success).data
            assertThat(joke.setup).isEqualTo("Test setup")
            assertThat(joke.punchline).isEqualTo("Test delivery")

            awaitComplete()
        }
    }

    @Test
    fun `getRandomJoke should return error when API call fails`() = runTest {
        // Given
        coEvery { mockApiService.getRandomJoke(any(), any(), any()) } throws IOException("Network error")

        // When
        repository.getRandomJoke().test {
            // Then
            val loadingResult = awaitItem()
            assertThat(loadingResult).isInstanceOf(ApiResult.Loading::class.java)

            val errorResult = awaitItem()
            assertThat(errorResult).isInstanceOf(ApiResult.Error::class.java)
            val exception = (errorResult as ApiResult.Error).exception
            assertThat(exception).isInstanceOf(NetworkException::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `getRandomJoke should return error when API returns error response`() = runTest {
        // Given
        val errorResponse = JokeResponse(
            error = true,
            message = "No jokes found"
        )
        coEvery { mockApiService.getRandomJoke(any(), any(), any()) } returns 
            Response.success(errorResponse)

        // When
        repository.getRandomJoke().test {
            // Then
            val loadingResult = awaitItem()
            assertThat(loadingResult).isInstanceOf(ApiResult.Loading::class.java)

            val errorResult = awaitItem()
            assertThat(errorResult).isInstanceOf(ApiResult.Error::class.java)
            val exception = (errorResult as ApiResult.Error).exception
            assertThat(exception).isInstanceOf(ApiException::class.java)

            awaitComplete()
        }
    }

    private fun createMockJokeResponse() = JokeResponse(
        error = false,
        type = "twopart",
        setup = "Test setup",
        delivery = "Test delivery",
        category = "Programming",
        id = 1,
        safe = true,
        lang = "en"
    )
}