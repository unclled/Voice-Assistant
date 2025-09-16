package com.project.voiceassistant.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalTypography = staticCompositionLocalOf {
    Typography()
}

object VoiceAssistantTheme {
    val colors: VoiceAssistantColors
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}

val LocalColors = staticCompositionLocalOf { LightColors }


@Composable
fun VoiceAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = VoiceAssistantTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
    )
}