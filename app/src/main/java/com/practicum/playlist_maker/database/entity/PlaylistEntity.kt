package com.practicum.playlist_maker.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey
    val playlistId: Long,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
)