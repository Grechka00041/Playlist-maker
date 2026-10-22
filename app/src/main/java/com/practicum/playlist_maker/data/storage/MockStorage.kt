package com.practicum.playlist_maker.data.storage

import com.practicum.playlist_maker.data.dto.TrackDto

class MockStorage : Storage {
    override suspend fun getTracks(): List<TrackDto> {
        return listOf(
            TrackDto(
                id = 1,
                trackName = "Yesterday (Remastered 2009)",
                artistName = "The Beatles",
                trackTimeMillis = 175000L, // 2:55
                image = "",
                favourite = false,
            ),
            TrackDto(
                id = 2,
                trackName = "Here Comes The Sun (Remastered 2009)",
                artistName = "The Beatles",
                trackTimeMillis = 241000L, // 4:01
                image = "",
                favourite = false
            ),
            TrackDto(
                id = 3,
                trackName = "No Reply",
                artistName = "The Beatles",
                trackTimeMillis = 312000L, // 5:12
                image = "",
                favourite = false
            ),
            TrackDto(
                id = 4,
                trackName = "Let It Be",
                artistName = "The Beatles",
                trackTimeMillis = 361000L, // 6:01
                image = "",
                favourite = false
            ),
            TrackDto(
                id = 5,
                trackName = "Girl",
                artistName = "The Beatles",
                trackTimeMillis = 251000L, // 4:11
                image = "",
                favourite = false
            ),
            TrackDto(
                id = 6,
                trackName = "Michelle",
                artistName = "The Beatles",
                trackTimeMillis = 181000L, // 3:01
                image = "",
                favourite = false
            ),
            TrackDto(
                id = 7,
                trackName = "Eleanor Rigby",
                artistName = "The Beatles",
                trackTimeMillis = 372000L, // 6:12
                image = "",
                favourite = false
            ),
            TrackDto(
                id = 8,
                trackName = "Come Together",
                artistName = "The Beatles",
                trackTimeMillis = 249000L, // 4:09
                image = "",
                favourite = false
            )
        )
    }
}