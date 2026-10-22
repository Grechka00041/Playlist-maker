package com.practicum.playlist_maker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.practicum.playlist_maker.ui.navigation.PlaylistHost
import com.practicum.playlist_maker.ui.navigation.Screen
import com.practicum.playlist_maker.ui.search.SearchScreen
import com.practicum.playlist_maker.ui.theme.PlaylistmakerTheme
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlaylistHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController(),
                        gson = remember { Gson() },
                        startDestination = Screen.MAIN.name,
                        searchViewModel = searchViewModel
                    )
                }
            }
        }
    }
}






