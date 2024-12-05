package io.github.ciriti.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.ciriti.profile.domain.model.Address
import io.github.ciriti.profile.domain.model.Company
import io.github.ciriti.profile.domain.model.User
import io.github.ciriti.auth.ui.AuthenticationState
import io.github.ciriti.auth.ui.AuthenticationViewModel
import io.github.ciriti.posts.domain.model.Post
import io.github.ciriti.posts.ui.HomeState
import io.github.ciriti.posts.ui.HomeViewModel
import io.github.ciriti.profile.ui.ProfileState
import io.github.ciriti.profile.ui.ProfileViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetupNavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreenNavigation() {
        // Arrange
        val mockHomeStateFlow = MutableStateFlow(
            HomeState(
                isLoading = false,
                posts = listOf(Post(id = 1, title = "Test Post", body = "Body", authorId = 1)),
                errorMessage = null
            )
        )
        val mockHomeViewModel = mockk<HomeViewModel> {
            every { state } returns mockHomeStateFlow
            every { handleIntent(any()) } just Runs
        }

        composeTestRule.setContent {
            SetupNavGraph(
                startDestination = Route.Posts.route,
                navController = rememberNavController(),
                drawerViewModel = mockk(relaxed = true), // Provide other mocked ViewModels
                authViewModel = mockk(relaxed = true),
                profileViewModel = mockk(relaxed = true),
                homeViewModel = mockHomeViewModel // Inject the mock here
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Test Post").assertIsDisplayed()
    }

    @Test
    fun testHomeScreenDisplaysMultiplePosts() {
        // Arrange
        val mockHomeStateFlow = MutableStateFlow(
            HomeState(
                isLoading = false,
                posts = listOf(
                    Post(id = 1, title = "Post 1", body = "Body 1", authorId = 1),
                    Post(id = 2, title = "Post 2", body = "Body 2", authorId = 2)
                ),
                errorMessage = null
            )
        )
        val mockHomeViewModel = mockk<HomeViewModel> {
            every { state } returns mockHomeStateFlow
            every { handleIntent(any()) } just Runs
        }

        // Act
        composeTestRule.setContent {
            SetupNavGraph(
                startDestination = Route.Posts.route,
                navController = rememberNavController(),
                drawerViewModel = mockk(relaxed = true),
                authViewModel = mockk(relaxed = true),
                profileViewModel = mockk(relaxed = true),
                homeViewModel = mockHomeViewModel
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Post 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Post 2").assertIsDisplayed()
    }


    @Test
    fun testStartDestinationDisplaysAuthenticationScreen() {
        // Arrange
        val mockAuthStateFlow = MutableStateFlow(
            AuthenticationState(isLoading = false)
        )
        val mockAuthViewModel = mockk<AuthenticationViewModel> {
            every { state } returns mockAuthStateFlow
            every { handleIntent(any()) } just Runs
        }

        // Act
        composeTestRule.setContent {
            SetupNavGraph(
                startDestination = Route.Authentication.route,
                navController = rememberNavController(),
                drawerViewModel = mockk(relaxed = true),
                authViewModel = mockAuthViewModel,
                profileViewModel = mockk(relaxed = true),
                homeViewModel = mockk(relaxed = true)
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Sign in with Google").assertIsDisplayed()
    }

    @Test
    fun testNavigationToHomeFromAuthentication() {
        // Arrange
        val mockAuthStateFlow = MutableStateFlow(
            AuthenticationState(isLoading = false)
        )
        val mockAuthViewModel = mockk<AuthenticationViewModel> {
            every { state } returns mockAuthStateFlow
            every { handleIntent(any()) } just Runs
        }

        val mockHomeStateFlow = MutableStateFlow(
            HomeState(
                isLoading = false,
                posts = listOf(Post(id = 1, title = "Test Post", body = "Body", authorId = 1)),
                errorMessage = null
            )
        )
        val mockHomeViewModel = mockk<HomeViewModel> {
            every { state } returns mockHomeStateFlow
            every { handleIntent(any()) } just Runs
        }

        composeTestRule.setContent {
            SetupNavGraph(
                startDestination = Route.Authentication.route,
                navController = rememberNavController(),
                drawerViewModel = mockk(relaxed = true),
                authViewModel = mockAuthViewModel,
                profileViewModel = mockk(relaxed = true),
                homeViewModel = mockHomeViewModel
            )
        }

        // Act
        composeTestRule.onNodeWithText("Sign in with Google").performClick()

        // Assert
        composeTestRule.onNodeWithText("Test Post").assertIsDisplayed()
    }

    @Test
    fun testNavigationToProfileFromHome() {
        // Arrange
        val mockProfileStateFlow = MutableStateFlow(profileState)
        val mockProfileViewModel = mockk<ProfileViewModel> {
            every { state } returns mockProfileStateFlow
            every { handleIntent(any()) } just Runs
        }

        val homeState = HomeState(
            isLoading = false,
            posts = listOf(Post(id = 1, title = "First Post", body = "Body", authorId = 1)),
            errorMessage = null
        )
        val mockHomeStateFlow = MutableStateFlow(homeState)
        val mockHomeViewModel = mockk<HomeViewModel> {
            every { state } returns mockHomeStateFlow
            every { handleIntent(any()) } just Runs
        }

        composeTestRule.setContent {
            SetupNavGraph(
                startDestination = Route.Posts.route,
                navController = rememberNavController(),
                drawerViewModel = mockk(relaxed = true),
                authViewModel = mockk(relaxed = true),
                profileViewModel = mockProfileViewModel,
                homeViewModel = mockHomeViewModel
            )
        }

        // Act
        composeTestRule.onNodeWithText("First Post").performClick()

        // Assert
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
    }

    @Test
    fun testLogOutNavigatesToAuthentication() {
        // Arrange
        val mockDrawerViewModel = mockk<DrawerViewModel> {
            every { logOut() } just Runs
        }
        val mockAuthViewModel = mockk<AuthenticationViewModel> {
            every { state } returns MutableStateFlow(
                AuthenticationState(
                    isLoading = false
                )
            )
        }
        val mockHomeViewModel = mockk<HomeViewModel> {
            every { state } returns MutableStateFlow(
                HomeState(
                    isLoading = false,
                    posts = listOf(Post(id = 1, title = "Test Post", body = "Body", authorId = 1)),
                    errorMessage = null
                )
            )
            every { handleIntent(any()) } just Runs
        }
        val mockProfileViewModel = mockk<ProfileViewModel> {
            every { state } returns MutableStateFlow(profileState)
        }

        composeTestRule.setContent {
            SetupNavGraph(
                startDestination = Route.Posts.route,
                navController = rememberNavController(),
                drawerViewModel = mockDrawerViewModel,
                authViewModel = mockAuthViewModel,
                homeViewModel = mockHomeViewModel,
                profileViewModel = mockProfileViewModel
            )
        }

        // Act
        composeTestRule.onNodeWithContentDescription("Menu").performClick() // Opens the drawer
        composeTestRule.onNodeWithText("Sign Out").performClick() // Logs out
        composeTestRule.onNodeWithText("Yes").performClick() // Logs out

        // Assert
        composeTestRule.onNodeWithText("Sign in with Google").assertIsDisplayed()
    }

}

private val profileState = ProfileState(
    isLoading = false,
    user = User(
        id = 1,
        fullName = "John Doe",
        email = "johndoe@example.com",
        address = Address(
            street = "123 Main St",
            suite = "Apt 4B",
            city = "New York",
            zipcode = "10001"
        ),
        phone = "123-456-7890",
        website = "www.johndoe.com",
        company = Company(
            name = "Doe Enterprises",
            catchPhrase = "Innovating the future",
            bs = "synergize scalable solutions"
        )
    ),
    errorMessage = null
)
