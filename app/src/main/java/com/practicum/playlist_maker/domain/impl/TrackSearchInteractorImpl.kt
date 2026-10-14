package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.domain.api.TrackSearchInteractor
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track

class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor {

    override fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }

    override suspend fun loadSomeData(): List<Track> {
        return repository.loadSomeData()
    }
}