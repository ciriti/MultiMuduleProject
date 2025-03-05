package com.example.posttemplate.posts.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.posttemplate.posts.ui.HomeScreen
import com.example.posttemplate.ui.navigation.Route

fun NavGraphBuilder.postsRoute(navController: NavHostController) {
    composable(route = Route.Posts.route) {
        HomeScreen(
            onRetry = { }, // TODO: retry mechanisms
            onNavigateToDetails = { postId ->
                navController.navigate(Route.Profile.passUserId(postId))
            }
        )
    }
}
