package com.practicum.playlist_maker.database.dao

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.practicum.playlist_maker.database.entity.PlaylistEntity
import com.practicum.playlist_maker.database.entity.PlaylistTrackCrossRef
import com.practicum.playlist_maker.database.entity.TrackEntity

data class PlaylistWithTracks(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val tracks: List<TrackEntity>
)
