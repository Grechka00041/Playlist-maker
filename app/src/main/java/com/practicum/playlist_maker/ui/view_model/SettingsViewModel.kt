package com.practicum.playlist_maker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlist_maker.database.preferences.ThemeManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val themeManager: ThemeManager) : ViewModel() {
    val isDarkTheme: StateFlow<Boolean> = themeManager.getThemeFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            themeManager.setDarkTheme(isDark)
        }
    }
}