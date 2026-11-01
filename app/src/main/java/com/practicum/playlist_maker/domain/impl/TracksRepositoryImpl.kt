package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.dto.TrackDto
import com.practicum.playlist_maker.data.dto.TracksSearchRequest
import com.practicum.playlist_maker.data.dto.TracksSearchResponse
import com.practicum.playlist_maker.database.AppDatabase
import com.practicum.playlist_maker.database.dao.toEntity
import com.practicum.playlist_maker.database.dao.toTrack
import com.practicum.playlist_maker.database.entity.PlaylistTrackCrossRef
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val database: AppDatabase,
    private val networkClient: NetworkClient
) : TracksRepository {

    private val dao = database.tracksDao()
    private val playlistTrackDao = database.playlistTrackDao()

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
        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
    }

    override suspend fun addTrackToPlaylist(trackId: Long, playlistId: Long) {
        playlistTrackDao.addTrackToPlaylist(
            PlaylistTrackCrossRef(
                playlistId = playlistId,
                trackId = trackId
            )
        )
    }

    override suspend fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long) {
        playlistTrackDao.removeTrackFromPlaylist(trackId, playlistId)
    }


    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        dao.insertTrack(track.copy(favorite = isFavorite).toEntity())
    }

    override suspend fun insertTrack(track: Track) {
        dao.insertTrack(track.toEntity())
    }


    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getFavoriteTracks().map {
            entities -> entities.map { it.toTrack()  }
        }
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