package com.example.posttemplate.profile.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.posttemplate.profile.ui.ProfileScreen
import com.example.posttemplate.ui.navigation.Route

fun NavGraphBuilder.profileRoute() {
    composable(
        route = Route.Profile.route + "/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { backStackEntry ->
        val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
        ProfileScreen(
            userId = userId,
            onBack = { /* Handle navigation back */ }
        )
    }
}
