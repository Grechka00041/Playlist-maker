package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val database: DatabaseMock
) : SearchHistoryRepository {

    override suspend fun getHistory(): List<String> {
        return database.getHistory()
    }

    override fun addToHistory(word: String) {
        database.addToHistory(word= word)
    }
}