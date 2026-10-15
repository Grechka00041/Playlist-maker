package com.practicum.playlist_maker.data

import com.practicum.playlist_maker.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}