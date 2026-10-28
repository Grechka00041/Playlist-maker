package com.practicum.playlist_maker.data.dto

data class TracksSearchResponse(
    val resultCount: Int,
    val results: List<TrackDto>
) : BaseResponse()