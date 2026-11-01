package com.practicum.playlist_maker.database.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

object ThemePreferences {
    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
}