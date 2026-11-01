package com.practicum.playlist_maker.database.entity

import androidx.room.Entity


@Entity(
    tableName = "playlist_track_cross_ref",
    primaryKeys = ["playlistId", "trackId"]
)
data class PlaylistTrackCrossRef(
    val playlistId: Long,
    val trackId: Long
)
