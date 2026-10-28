package com.practicum.playlist_maker.domain.api

interface SearchHistoryRepository {

    suspend fun getHistory(): List<String>

    fun addToHistory(word: String)
}