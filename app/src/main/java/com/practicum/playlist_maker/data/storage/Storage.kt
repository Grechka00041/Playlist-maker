package com.practicum.playlist_maker.data.storage

import com.practicum.playlist_maker.data.dto.TrackDto

interface Storage {
    suspend fun getTracks(): List<TrackDto>
}