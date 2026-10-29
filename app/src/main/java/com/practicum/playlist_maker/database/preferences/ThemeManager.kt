package com.practicum.playlist_maker.database.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeManager(
    private val dataStore: DataStore<Preferences>) {

    fun getThemeFlow(): Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[ThemePreferences.IS_DARK_THEME] ?: false
    }

    suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { prefs ->
            prefs[ThemePreferences.IS_DARK_THEME] = isDark
        }
    }
}