package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val database: DatabaseMock
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        return database.searchTracks(expression)
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun addTrackToPlaylist(trackId: Long, playlistId: Long) {
        database.addTrackToPlaylist(trackId, playlistId)
    }

    override suspend fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long) {
        database.removeTrackFromPlaylist(trackId, playlistId)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override suspend fun getTrackById(id: Long): Track? {
        return database.getTrackById(id)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }
}