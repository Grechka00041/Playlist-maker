package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.dto.TrackDto
import com.practicum.playlist_maker.data.dto.TracksSearchRequest
import com.practicum.playlist_maker.data.dto.TracksSearchResponse
import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val database: DatabaseMock,
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        return if (response.resultCode == 200 && response is TracksSearchResponse) {
            response.results.mapNotNull { dto ->
                if (dto.trackName.isEmpty() || dto.artistName.isEmpty()) null
                else mapToDomain(dto)
            }
        } else {
            throw Exception(response.errorMessage)
        }
    }

    override suspend fun getTrackByNameAndArtist(track: Track): Flow<Track?> {

        val query = "${track.trackName} ${track.artistName}"

        return flow {
            try {
                val response = networkClient.doRequest(TracksSearchRequest(query))

                if (response.resultCode == 200 && response is TracksSearchResponse) {
                    val foundDto = response.results.firstOrNull { dto ->
                        dto.trackName.equals(track.trackName, ignoreCase = true) &&
                                dto.artistName.equals(track.artistName, ignoreCase = true)
                    }
                    emit(foundDto?.let { mapToDomain(it) })
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                emit(null)
            }
        }
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

    override suspend fun insertTrack(track: Track) {
        database.insertTrack(track)
    }


    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    private fun mapToDomain(dto: TrackDto): Track {
        val totalSeconds = (dto.trackTimeMillis) / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return Track(
            id = dto.id,
            trackName = dto.trackName,
            artistName = dto.artistName,
            trackTime = "%02d:%02d".format(minutes, seconds),
            image = dto.image?.replace("100x100", "512x512") ?: "",
            favorite = false
        )
    }
}