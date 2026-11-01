package com.practicum.playlist_maker.database.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
) {
    companion object {
        private val HISTORY_KEY = stringPreferencesKey("search_history")
        private const val SEPARATOR = ","
        private const val MAX_ENTRIES = 10
    }

    fun addEntry(word: String) {
        if (word.isBlank()) return

        scope.launch {

            dataStore.edit { prefs ->
                val historyString = prefs[HISTORY_KEY].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else mutableListOf()

                history.remove(word)
                history.add(0, word)

                prefs[HISTORY_KEY] = history.take(MAX_ENTRIES).joinToString(SEPARATOR)
            }
        }
    }

    suspend fun getEntries(): List<String> {
        return dataStore.data.first().let { prefs ->
            val historyString = prefs[HISTORY_KEY].orEmpty()
            if (historyString.isEmpty()) emptyList()
            else historyString.split(SEPARATOR)
        }
    }
}