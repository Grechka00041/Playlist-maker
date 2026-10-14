package com.practicum.playlist_maker.data.network

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.dto.BaseResponse
import com.practicum.playlist_maker.data.dto.TracksSearchResponse
import kotlin.collections.listOf

class RetrofitNetworkClient : NetworkClient {
    override fun doRequest(dto: Any): BaseResponse {
        return TracksSearchResponse(listOf())
    }
}