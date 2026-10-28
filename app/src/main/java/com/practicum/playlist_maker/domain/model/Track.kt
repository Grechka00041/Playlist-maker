package com.practicum.playlist_maker.domain.model

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favorite: Boolean,
)