package com.project.voiceassistant.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val accentColor = Color(0xE0FFC107)
val whiteColor = Color(0xFFFFFFFF)
val greyColor = Color(0xFF343434)

val LightColors = VoiceAssistantColors(
    primaryContentColor = Color(0xFF2C2C2C),
    secondaryContentColor = Color(0xE62C2C2C),
    placeholderContentColor = whiteColor,
    accentColor = accentColor,
    componentBackgroundColor = Color(0xCC011C3A),
    backgroundColor = Color(0xFFFCFCFF),
    headerColor = Color(0x99011C3A)
)

val DarkColors = VoiceAssistantColors(
    primaryContentColor = whiteColor,
    secondaryContentColor = Color(0xE6FCFCFF),
    placeholderContentColor = whiteColor,
    accentColor = accentColor,
    componentBackgroundColor = greyColor,
    backgroundColor = greyColor,
    headerColor = Color(0xFF232323)
)

@Immutable
data class VoiceAssistantColors(
    val primaryContentColor: Color,
    val secondaryContentColor: Color,
    val placeholderContentColor: Color,
    val accentColor: Color,
    val componentBackgroundColor: Color,
    val backgroundColor: Color,
    val headerColor: Color
)