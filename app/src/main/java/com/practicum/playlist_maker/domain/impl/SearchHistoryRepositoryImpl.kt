package com.practicum.playlist_maker.domain.impl

import com.practicum.playlist_maker.database.preferences.SearchHistoryPreferences
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override suspend fun getHistory(): List<String> {
        return preferences.getEntries()
    }

    override fun addToHistory(word: String) {
        preferences.addEntry(word)
    }
}