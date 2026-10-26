package com.practicum.playlist_maker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.practicum.playlist_maker.ui.favorite.FavoriteScreen
import com.practicum.playlist_maker.ui.main.MainScreen
import com.practicum.playlist_maker.ui.playlist.AddPlaylistScreen
import com.practicum.playlist_maker.ui.playlist.PlaylistsScreen
import com.practicum.playlist_maker.ui.search.SearchScreen
import com.practicum.playlist_maker.ui.settings.SettingsScreen
import com.practicum.playlist_maker.ui.track.TrackDetailsScreen
import com.practicum.playlist_maker.ui.view_model.PlaylistsViewModel
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import com.practicum.playlist_maker.ui.view_model.TrackDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlaylistHost(
    modifier: Modifier = Modifier,
    startDestination: String = "main",
    navController: NavHostController,
    gson: Gson,
    searchViewModel: SearchViewModel,
    playlistsViewModel: PlaylistsViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Screen.MAIN.name) {
            MainScreen(
                onNavigateToSearch = { navController.navigateToSearch() },
                onNavigateToSettings = { navController.navigateToSettings() },
                onNavigateToPlaylsists = { navController.navigate(Screen.PLAYLISTS.name) },
                onNavigateToFavorite = { navController.navigate(Screen.FAVORITE.name) }
            )
        }

        composable(Screen.SEARCH.name) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onNavigateToDetailScreen = { track ->
                    navController.navigate("${Screen.TRACK_DETAILS.name}/${track.id}")
                },
                onNavigateBack = { navController.navigateBack() },
                modifier = Modifier
            )
        }

        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                onNavigateBack = { navController.navigateBack() }
            )
        }


        composable(Screen.FAVORITE.name) {
            FavoriteScreen(
                onNavigateBack = { navController.navigateBack() }
            )
        }

        composable(Screen.PLAYLISTS.name) {
            PlaylistsScreen(
                modifier = Modifier,
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = { navController.navigate(Screen.ADD_PLAYLIST.name) },
                navigateToPlaylist = { },
                navigateBack = { navController.navigateBack()}
            )
        }

        composable(Screen.ADD_PLAYLIST.name) {
            AddPlaylistScreen(
                onNavigateBack = { navController.navigateBack() },
                playlistsViewModel = playlistsViewModel
            )
        }

        composable("${Screen.TRACK_DETAILS.name}/{trackId}") { backStackEntry ->
            val trackIdString = backStackEntry.arguments?.getString("trackId")
            val trackId = trackIdString?.toLongOrNull()

            if (trackId != null) {
                val viewModel: TrackDetailsViewModel = koinViewModel(
                    parameters = { parametersOf(trackId) }
                )

                TrackDetailsScreen(
                    onNavigateBack = { navController.navigateBack() },
                    modifier = Modifier,
                    trackDetailsViewModel = viewModel
                )
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
