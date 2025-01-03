package com.example.posttemplate.ui.navigation

sealed class Route(val route: String) {
    data object Authentication : Route(route = "authentication_screen")
    data object Posts : Route("home")
    data object Downloads : Route("downloads")
    data object Profile : Route("profile") {
        fun passUserId(userId: Int): String {
            return "profile/$userId"
        }
    }
}
