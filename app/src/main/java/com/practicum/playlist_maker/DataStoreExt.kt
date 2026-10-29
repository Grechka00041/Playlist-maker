package com.practicum.playlist_maker

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.searchHistoryDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "search_history"
)