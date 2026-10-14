package com.practicum.playlist_maker.domain.api

import com.practicum.playlist_maker.domain.model.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): List<Track>
    suspend fun loadSomeData():List<Track>
}