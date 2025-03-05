package com.example.posttemplate.posts.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.posttemplate.posts.domain.model.Post
import org.junit.Rule
import org.junit.Test

class PostsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingStateDisplaysLoadingIndicator() {
        // Arrange
        val state = HomeState.Loading

        // Act
        composeTestRule.setContent {
            HomeScreenContent(
                state = state,
                onRetry = {},
                onNavigateToDetails = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun testErrorStateDisplaysErrorMessage() {
        // Arrange
        val errorMessage = "An error occurred"
        val state = HomeState.Error(errorMessage)

        // Act
        composeTestRule.setContent {
            HomeScreenContent(
                state = state,
                onRetry = {},
                onNavigateToDetails = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun testSuccessStateDisplaysListOfPosts() {
        // Arrange
        val posts = listOf(
            Post(id = 1, title = "First Post", body = "Body of the first post", authorId = 1),
            Post(id = 2, title = "Second Post", body = "Body of the second post", authorId = 2)
        )
        val state = HomeState.Success(posts)

        // Act
        composeTestRule.setContent {
            HomeScreenContent(
                state = state,
                onRetry = {},
                onNavigateToDetails = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("First Post").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Post").assertIsDisplayed()
    }

    @Test
    fun testPostClickNavigatesToDetails() {
        // Arrange
        val posts = listOf(
            Post(id = 1, title = "First Post", body = "Body of the first post", authorId = 1)
        )
        var clickedPostId: Int? = null
        val state = HomeState.Success(posts)

        composeTestRule.setContent {
            HomeScreenContent(
                state = state,
                onRetry = {},
                onNavigateToDetails = { clickedPostId = it }
            )
        }

        // Act
        composeTestRule.onNodeWithText("First Post").performClick()

        // Assert
        assert(clickedPostId == 1)
    }
}
