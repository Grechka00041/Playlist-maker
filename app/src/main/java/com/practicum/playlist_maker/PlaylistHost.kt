package com.practicum.playlist_maker

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun PlaylistHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.name
    ) {

        composable(Screen.MAIN.name) {
            MainScreen(
                onNavigateToSearch = { navController.navigateToSearch() },
                onNavigateToSettings = { navController.navigateToSettings() }
            )
        }

        composable(Screen.SEARCH.name) {
            SearchScreen(
                onNavigateBack = { navController.navigateBack() }
            )
        }

        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                onNavigateBack = { navController.navigateBack() }
            )
        }
    }
}

fun NavController.navigateToSearch() {
    navigate(Screen.SEARCH.name)
}

fun NavController.navigateToSettings() {
    navigate(Screen.SETTINGS.name)
}

fun NavController.navigateBack() {
    popBackStack()
}