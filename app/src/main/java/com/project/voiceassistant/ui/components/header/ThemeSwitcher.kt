package com.project.voiceassistant.ui.components.header

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun ThemeSwitch(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = isDarkTheme,
        onCheckedChange = onThemeChange,
        thumbContent = {
            Icon(
                painter = if (isDarkTheme) {
                    painterResource(R.drawable.night_mode)
                } else {
                    painterResource(R.drawable.light_mode)
                },
                modifier = Modifier.size(dimensionResource(R.dimen.icon_switch_size)),
                contentDescription = if (isDarkTheme) {
                    stringResource(R.string.dark_mode)
                } else {
                    stringResource(R.string.light_mode)
                },
                tint = VoiceAssistantTheme.colors.backgroundColor
            )
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = VoiceAssistantTheme.colors.primaryContentColor,
            uncheckedThumbColor = VoiceAssistantTheme.colors.primaryContentColor,
            checkedTrackColor = VoiceAssistantTheme.colors.accentColor,
            uncheckedTrackColor = VoiceAssistantTheme.colors.secondaryContentColor
        )
    )
}

@Preview
@Composable
private fun ThemeSwitchPreview() {
    VoiceAssistantTheme(darkTheme = false) {
        ThemeSwitch(isDarkTheme = false, onThemeChange = {})
    }
}

@Preview
@Composable
private fun ThemeSwitchDarkPreview() {
    VoiceAssistantTheme(darkTheme = true) {
        ThemeSwitch(isDarkTheme = true, onThemeChange = {})
    }
}