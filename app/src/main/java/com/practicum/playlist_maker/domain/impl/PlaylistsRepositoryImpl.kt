package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.api.PlaylistsRepository
import com.practicum.playlist_maker.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val database: DatabaseMock
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        database.addNewPlaylist(name, description, coverImageUri)
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(playlistId = id)
    }
}