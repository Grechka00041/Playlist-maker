package com.practicum.playlist_maker.domain.api

import com.practicum.playlist_maker.domain.model.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

}