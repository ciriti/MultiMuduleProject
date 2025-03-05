package com.example.posttemplate.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.posttemplate.auth.ui.AuthenticationScreen
import com.example.posttemplate.ui.navigation.Route

fun NavGraphBuilder.authenticationRoute(
    navController: NavHostController
) {
    composable(route = Route.Authentication.route) {
        AuthenticationScreen(
            navigateToHome = {
                navController.navigate(Route.Posts.route) {
                    popUpTo(Route.Authentication.route) { inclusive = true }
                }
            }
        )
    }
}
