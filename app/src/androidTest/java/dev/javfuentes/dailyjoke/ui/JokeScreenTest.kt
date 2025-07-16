package dev.javfuentes.dailyjoke.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.javfuentes.dailyjoke.data.Joke
import dev.javfuentes.dailyjoke.data.JokeType
import dev.javfuentes.dailyjoke.data.model.*
import dev.javfuentes.dailyjoke.data.repository.JokeRepository
import dev.javfuentes.dailyjoke.ui.screens.JokeScreen
import dev.javfuentes.dailyjoke.ui.theme.DailyJokeTheme
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModel
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JokeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockRepository = mockk<JokeRepository>()
    private lateinit var viewModel: JokeViewModel

    @Before
    fun setup() {
        viewModel = JokeViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun jokeScreen_displaysLoadingIndicator_whenLoading() {
        // Given
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Loading())

        // When
        composeTestRule.setContent {
            DailyJokeTheme {
                JokeScreen(viewModel = viewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Loading joke...").assertIsDisplayed()
    }

    @Test
    fun jokeScreen_displaysJoke_whenSuccess() {
        // Given
        val mockJoke = Joke(
            id = 1,
            category = "Programming",
            setup = "Why do programmers prefer dark mode?",
            punchline = "Because light attracts bugs!",
            type = JokeType.TWOPART,
            safe = true,
            lang = "en"
        )
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))

        // When
        composeTestRule.setContent {
            DailyJokeTheme {
                JokeScreen(viewModel = viewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Why do programmers prefer dark mode?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Because light attracts bugs!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Programming").assertIsDisplayed()
        composeTestRule.onNodeWithText("Joke #1").assertIsDisplayed()
    }

    @Test
    fun jokeScreen_displaysErrorMessage_whenError() {
        // Given
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Error(NetworkException("Network error"))
        )

        // When
        composeTestRule.setContent {
            DailyJokeTheme {
                JokeScreen(viewModel = viewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Network error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try Again").assertIsDisplayed()
    }

    @Test
    fun jokeScreen_retriesOnButtonClick() {
        // Given
        coEvery { mockRepository.getRandomJoke() } returns flowOf(
            ApiResult.Error(NetworkException("Network error"))
        )

        composeTestRule.setContent {
            DailyJokeTheme {
                JokeScreen(viewModel = viewModel)
            }
        }

        // When
        composeTestRule.onNodeWithText("Try Again").performClick()

        // Then
        coVerify { mockRepository.getRandomJoke() }
    }

    @Test
    fun jokeScreen_refreshesOnNewJokeButtonClick() {
        // Given
        val mockJoke = createMockJoke()
        coEvery { mockRepository.getRandomJoke() } returns flowOf(ApiResult.Success(mockJoke))

        composeTestRule.setContent {
            DailyJokeTheme {
                JokeScreen(viewModel = viewModel)
            }
        }

        // When
        composeTestRule.onNodeWithText("New Joke").performClick()

        // Then
        coVerify(atLeast = 2) { mockRepository.getRandomJoke() }
    }

    private fun createMockJoke() = Joke(
        id = 1,
        category = "Programming",
        setup = "Test setup",
        punchline = "Test punchline",
        type = JokeType.TWOPART,
        safe = true,
        lang = "en"
    )
}