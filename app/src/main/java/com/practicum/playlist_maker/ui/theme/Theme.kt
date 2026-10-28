package com.practicum.playlist_maker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    background = WhitePrimary,
    surface = WhitePrimary,
    onPrimary = WhitePrimary,
    onBackground = BlackPrimary,
    onSurface = BlackPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    background = BlackPrimary,
    surface = BlackPrimary,
    onPrimary = WhitePrimary,
    onBackground = WhitePrimary,
    onSurface = WhitePrimary
)
@Composable
fun PlaylistmakerTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}