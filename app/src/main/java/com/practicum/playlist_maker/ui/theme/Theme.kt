package com.practicum.playlist_maker.ui.theme

import androidx.compose.material3.MaterialTheme
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

@Composable
fun PlaylistmakerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}