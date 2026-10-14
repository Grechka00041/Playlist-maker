package com.practicum.playlist_maker.domain.api

import com.practicum.playlist_maker.domain.model.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
    suspend fun loadSomeData():List<Track>

    suspend fun loadTrackDetail(trackId: Long): Track

    suspend fun searchTracksContains(expression: String) : List<Track>
}