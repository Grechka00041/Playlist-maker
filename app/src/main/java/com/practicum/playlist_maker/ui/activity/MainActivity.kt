package com.practicum.playlist_maker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.practicum.playlist_maker.database.preferences.ThemeManager
import com.practicum.playlist_maker.ui.navigation.PlaylistHost
import com.practicum.playlist_maker.ui.navigation.Screen
import com.practicum.playlist_maker.ui.theme.PlaylistmakerTheme
import com.practicum.playlist_maker.ui.view_model.PlaylistsListViewModel
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModel()
    private val playlistsListViewModel: PlaylistsListViewModel by viewModel()
    private val themeManager: ThemeManager by lazy { getKoin().get() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark by themeManager.getThemeFlow().collectAsStateWithLifecycle(initialValue = false)

            PlaylistmakerTheme(isDark) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlaylistHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController(),
                        gson = remember { Gson() },
                        startDestination = Screen.MAIN.name,
                        searchViewModel = searchViewModel,
                        playlistsListViewModel = playlistsListViewModel
                    )
                }
            }
        }
    }

}
