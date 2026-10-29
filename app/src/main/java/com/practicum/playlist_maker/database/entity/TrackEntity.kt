package com.practicum.playlist_maker.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    val favorite: Boolean = false
)