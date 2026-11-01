package com.practicum.playlist_maker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

import com.practicum.playlist_maker.database.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPlaylist(playlist: PlaylistEntity): Long

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistWithTracks>>
    @Query("DELETE FROM playlists WHERE playlistId = :id")
    suspend fun deletePlaylistById(id: Long)
}

