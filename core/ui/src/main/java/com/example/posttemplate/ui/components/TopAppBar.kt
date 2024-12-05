package io.github.ciriti.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.ciriti.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    currentDestination: String?,
    onNavigateBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when (currentDestination) {
                    Route.Posts.route -> "Home"
                    Route.Profile.route -> "Profile"
                    else -> "App"
                }
            )
        },
        navigationIcon = {
            if (currentDestination != Route.Posts.route) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }
        },
        actions = {
            if (currentDestination == Route.Posts.route) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewTopAppBar() {
    TopAppBar(
        currentDestination = Route.Posts.route,
        onNavigateBack = { /* Handle back navigation */ },
        onMenuClick = { /* Handle search click */ },
        onSearchClick = { /* Handle search click */ }
    )
}
