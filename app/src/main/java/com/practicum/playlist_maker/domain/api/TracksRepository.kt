package com.practicum.playlist_maker.domain.api

import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun addTrackToPlaylist(trackId: Long, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)

    suspend fun getTrackById(id: Long): Track?

}