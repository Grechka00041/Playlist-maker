package com.practicum.playlist_maker.ui.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.practicum.playlist_maker.domain.model.Track

@Composable
fun DetailsScreen(track: Track) {
    Text(track.toString()) //will be implemented later
}