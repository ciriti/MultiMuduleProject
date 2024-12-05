package io.github.ciriti.auth.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.github.ciriti.auth.ui.AuthenticationIntent
import io.github.ciriti.auth.ui.AuthenticationScreen
import io.github.ciriti.auth.ui.AuthenticationViewModel
import io.github.ciriti.ui.navigation.Route

fun NavGraphBuilder.authenticationRoute(
    navController: NavHostController,
    authViewModel: AuthenticationViewModel
) {
    composable(route = Route.Authentication.route) {
        AuthenticationScreen(
            loadingState = authViewModel.state.collectAsState().value.isLoading,
            onButtonClicked = {
                authViewModel.handleIntent(AuthenticationIntent.Authenticate)
            },
            navigateToHome = {
                navController.navigate(Route.Posts.route) {
                    popUpTo(Route.Authentication.route) { inclusive = true }
                }
            }
        )
    }
}
