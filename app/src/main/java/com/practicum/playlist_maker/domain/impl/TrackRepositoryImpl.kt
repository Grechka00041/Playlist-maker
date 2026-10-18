package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.data.dto.TracksSearchResponse
import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.dto.TracksSearchRequest
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(2.seconds)
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        return if (response.resultCode == 200) {
            val dtoList = (response as TracksSearchResponse).results

            dtoList
                .filter { dto ->
                    dto.trackName.contains(expression, ignoreCase = true) ||
                            dto.artistName.contains(expression, ignoreCase = true)
                }
                .map { dto ->
                    val seconds = dto.trackTimeMillis / 1000
                    val minutes = seconds / 60
                    val trackTime = "%02d:%02d".format(minutes, seconds % 60)

                    Track(
                        id = dto.id,
                        trackName = dto.trackName,
                        artistName = dto.artistName,
                        trackTime = trackTime
                    )
                }
        } else {
            throw Exception("Ошибка сервера: код ${response.resultCode}")
        }
    }
}