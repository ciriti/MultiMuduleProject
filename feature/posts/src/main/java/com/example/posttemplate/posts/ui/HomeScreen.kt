package io.github.ciriti.posts.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.ciriti.posts.domain.model.Post
import io.github.ciriti.posts.ui.components.PostItem
import io.github.ciriti.ui.components.LoadingIndicator

@Composable
fun HomeScreen(
    state: HomeState,
    onRetry: () -> Unit,
    onNavigateToDetails: (Int) -> Unit
) {
    when {
        state.isLoading -> LoadingIndicator()
        state.errorMessage != null -> Text("Error: ${state.errorMessage}")
        else -> LazyColumn {
            items(state.posts) { post ->
                PostItem(post = post) { onNavigateToDetails(post.id) }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(
            isLoading = false,
            posts = listOf(
                Post(
                    id = 1,
                    title = "First Post",
                    body = "This is the body of the first post.",
                    authorId = 1
                ),
                Post(
                    id = 2,
                    title = "Second Post",
                    body = "This is the body of the second post.",
                    authorId = 2
                )
            ),
            errorMessage = null
        ),
        onRetry = {},
        onNavigateToDetails = {}
    )
}
