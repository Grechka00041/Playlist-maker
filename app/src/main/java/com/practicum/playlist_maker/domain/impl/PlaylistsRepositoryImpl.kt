package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.database.AppDatabase
import com.practicum.playlist_maker.database.dao.toDomainPlaylist
import com.practicum.playlist_maker.database.entity.PlaylistEntity
import com.practicum.playlist_maker.domain.api.PlaylistsRepository
import com.practicum.playlist_maker.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    private val playlistsDao = database.playlistsDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsDao.getAllPlaylists().map { list ->
            list.find { it.playlist.playlistId == playlistId }?.toDomainPlaylist()
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getAllPlaylists().map { entities ->
            entities.map { it.toDomainPlaylist() }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        val entity = PlaylistEntity(
            playlistId = 0,
            name = name,
            description = description,
            coverImageUri = coverImageUri
        )
        playlistsDao.addNewPlaylist(entity)
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistsDao.deletePlaylistById(id)
    }
}