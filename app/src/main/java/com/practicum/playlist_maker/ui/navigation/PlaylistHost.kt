package com.practicum.playlist_maker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.main.MainScreen
import com.practicum.playlist_maker.ui.search.DetailsScreen
import com.practicum.playlist_maker.ui.search.SearchScreen
import com.practicum.playlist_maker.ui.settings.SettingsScreen
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import kotlin.jvm.java

@Composable
fun PlaylistHost(
    modifier: Modifier = Modifier,
    startDestination: String = "main",
    navController: NavHostController,
    gson: Gson,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Screen.MAIN.name) {
            MainScreen(
                onNavigateToSearch = { navController.navigateToSearch() },
                onNavigateToSettings = { navController.navigateToSettings() }
            )
        }

        composable(Screen.SEARCH.name) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onNavigateToDetailScreen = { track ->
                    val trackJson = gson.toJson(track)
                    navController.navigate("details/$trackJson")
                },
                onNavigateBack = { navController.navigateBack() }
            )
        }

        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                onNavigateBack = { navController.navigateBack() }
            )
        }

        composable(
            route = "details/{trackJson}"
        ) { backStackEntry ->
            val trackJson = backStackEntry.arguments?.getString("trackJson")
            if (trackJson != null) {
                val track = gson.fromJson(trackJson, Track::class.java)
                DetailsScreen(track = track)
            }
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