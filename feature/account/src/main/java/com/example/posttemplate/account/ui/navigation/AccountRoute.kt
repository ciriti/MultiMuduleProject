package com.example.posttemplate.account.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.posttemplate.account.ui.AccountScreen
import com.example.posttemplate.ui.navigation.Route

fun NavGraphBuilder.accountRoute() {
    composable(
        route = Route.Account.route + "/{accountId}",
        arguments = listOf(navArgument("accountId") { type = NavType.IntType })
    ) { backStackEntry ->
        val accountId = backStackEntry.arguments?.getInt("accountId") ?: return@composable

        AccountScreen(
            accountId = accountId,
            onBack = { /* Handle navigation back */ },
        )
    }
}
