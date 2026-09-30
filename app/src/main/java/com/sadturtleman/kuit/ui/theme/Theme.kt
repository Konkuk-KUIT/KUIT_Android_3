package com.sadturtleman.kuit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BookmarkColorBackground,
    background = BackgroundColor,
    surface = BackgroundColor,
    surfaceVariant = CardColor,
    onBackground = TitleColor,
    onSurface = TitleColor,
    onSurfaceVariant = SubTextColor
)

@Composable
fun KuitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}