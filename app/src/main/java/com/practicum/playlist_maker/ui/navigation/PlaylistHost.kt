package com.practicum.playlist_maker.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.favorite.FavoriteScreen
import com.practicum.playlist_maker.ui.main.MainScreen
import com.practicum.playlist_maker.ui.playlist.AddPlaylistScreen
import com.practicum.playlist_maker.ui.playlist.PlaylistDetailsScreen
import com.practicum.playlist_maker.ui.playlist.PlaylistsScreen
import com.practicum.playlist_maker.ui.search.SearchScreen
import com.practicum.playlist_maker.ui.settings.SettingsScreen
import com.practicum.playlist_maker.ui.track.TrackDetailsScreen
import com.practicum.playlist_maker.ui.view_model.PlaylistDetailsViewModel
import com.practicum.playlist_maker.ui.view_model.PlaylistsListViewModel
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
    playlistsListViewModel: PlaylistsListViewModel,
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
                    val json = Uri.encode(gson.toJson(track))
                    navController.navigate("${Screen.TRACK_DETAILS.name}/$json")
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
                playlistsListViewModel = playlistsListViewModel,
                addNewPlaylist = { navController.navigate(Screen.ADD_PLAYLIST.name) },
                navigateToPlaylist = { playlistId ->
                    navController.navigate("${Screen.PLAYLIST_DETAILS.name}/$playlistId")
                },
                navigateBack = { navController.navigateBack()}
            )
        }

        composable("${Screen.PLAYLIST_DETAILS.name}/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId")?.toLongOrNull()

            if (playlistId != null) {
                val viewModel: PlaylistDetailsViewModel = koinViewModel(
                    parameters = { parametersOf(playlistId) }
                )

                PlaylistDetailsScreen(
                    onNavigateBack = { navController.navigateBack() },
                    onTrackClick = { track ->
                        val json = Uri.encode(gson.toJson(track))
                        navController.navigate("${Screen.TRACK_DETAILS.name}/$json")
                    },
                    playlistDetailsViewModel = viewModel
                )
            }
        }

        composable(Screen.ADD_PLAYLIST.name) {
            AddPlaylistScreen(
                onNavigateBack = { navController.navigateBack() },
                playlistsListViewModel = playlistsListViewModel
            )
        }

        composable("${Screen.TRACK_DETAILS.name}/{trackJson}") { backStackEntry ->
            val trackJson = backStackEntry.arguments?.getString("trackJson")
            val track = trackJson?.let {
                try {
                    gson.fromJson(Uri.decode(it), Track::class.java)
                } catch (_: Exception) {
                    null
                }
            }

            if (track != null) {
                val viewModel: TrackDetailsViewModel = koinViewModel(
                    parameters = { parametersOf(track) }
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
