package com.example.posttemplate.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.posttemplate.auth.ui.AuthenticationViewModel
import com.example.posttemplate.auth.ui.navigation.authenticationRoute
import com.example.posttemplate.posts.ui.HomeViewModel
import com.example.posttemplate.posts.ui.navigation.postsRoute
import com.example.posttemplate.profile.ui.ProfileViewModel
import com.example.posttemplate.profile.ui.navigation.profileRoute
import com.example.posttemplate.ui.components.AdaptiveNavigationDrawer
import com.example.posttemplate.ui.components.DisplayAlertDialog
import com.example.posttemplate.ui.components.DrawerContent
import com.example.posttemplate.ui.components.TopAppBar
import com.example.posttemplate.ui.components.isLargeScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    drawerViewModel: DrawerViewModel = koinInject<DrawerViewModel>(),
    authViewModel: AuthenticationViewModel = koinInject<AuthenticationViewModel>(),
    profileViewModel: ProfileViewModel = koinInject<ProfileViewModel>(),
    homeViewModel: HomeViewModel = koinInject<HomeViewModel>(),
) {
    val isLargeScreen = isLargeScreen()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var signOutDialogOpened by remember { mutableStateOf(false) }

    AdaptiveNavigationDrawer(
        isLargeScreen = isLargeScreen,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onLogOut = {
                    signOutDialogOpened = true
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (currentDestination != Route.Authentication.route) {
                    TopAppBar(
                        currentDestination = currentDestination,
                        onNavigateBack = { navController.popBackStack() },
                        onMenuClick = {
                            if (!isLargeScreen) {
                                scope.launch { drawerState.open() }
                            }
                        },
                        onSearchClick = { /* Handle search click */ }
                    )
                }
            }
        ) { innerPadding ->
            DisplayAlertDialog(
                title = "Sign Out",
                message = "Are you sure you want to sign out?",
                dialogOpened = signOutDialogOpened,
                onDialogClosed = { signOutDialogOpened = false },
                onYesClicked = {
                    drawerViewModel.logOut()
                    navController.navigate(Route.Authentication.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true // Clears all intermediate destinations
                        }
                        launchSingleTop = true // Ensures no duplicate destinations
                    }
                }
            )
            NavHost(
                startDestination = startDestination,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) {
                authenticationRoute(navController, authViewModel)
                postsRoute(navController, homeViewModel)
                profileRoute(profileViewModel)
            }
        }
    }
}
