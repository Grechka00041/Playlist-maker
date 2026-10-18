package com.practicum.playlist_maker.data.network

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.dto.BaseResponse
import com.practicum.playlist_maker.data.dto.TracksSearchRequest
import com.practicum.playlist_maker.data.dto.TracksSearchResponse
import com.practicum.playlist_maker.data.storage.Storage

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {
    override suspend fun doRequest(dto: Any): BaseResponse {
        return if (dto is TracksSearchRequest) {
            TracksSearchResponse(results = storage.getTracks()).apply {
                resultCode = 200
            }
        } else {
            TracksSearchResponse(results = emptyList()).apply {
                resultCode = 400
            }
        }
    }
}