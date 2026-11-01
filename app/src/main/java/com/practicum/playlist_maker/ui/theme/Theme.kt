package com.practicum.playlist_maker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = WhitePrimary,
    background = WhitePrimary,
    onBackground = BlackPrimary,
    surface = WhitePrimary,
    onSurface = BlackPrimary,
    surfaceVariant = GreyLight,
    onSurfaceVariant = GreyPrimary,
    outline = GreyPrimary,
    outlineVariant = GreyLight
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    onPrimary = WhitePrimary,
    background = BlackPrimary,
    onBackground = WhitePrimary,
    surface = BlackPrimary,
    onSurface = WhitePrimary,
    surfaceVariant = GreyPrimary.copy(alpha = 0.3f),
    onSurfaceVariant = GreyLight,
    outline = GreyPrimary,
    outlineVariant = GreyPrimary.copy(alpha = 0.5f)
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