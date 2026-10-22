package com.practicum.playlist_maker.data.storage

import com.practicum.playlist_maker.data.dto.TrackDto
import com.practicum.playlist_maker.domain.model.Track

interface Storage {
    suspend fun getTracks(): List<TrackDto>


}