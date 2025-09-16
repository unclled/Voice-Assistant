package com.project.voiceassistant.ui.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun ScreenHeader(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_12)),
        color = VoiceAssistantTheme.colors.headerColor,
        tonalElevation = dimensionResource(R.dimen.padding_4)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_16),
                    vertical = dimensionResource(id = R.dimen.padding_8)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = VoiceAssistantTheme.typography.titleLarge,
                color = VoiceAssistantTheme.colors.placeholderContentColor
            )
            Spacer(modifier = Modifier.weight(1f))
            ThemeSwitch(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_8)))
            Image(
                painter = painterResource(R.drawable.delete),
                modifier = Modifier
                    .size(dimensionResource(R.dimen.icon_size_28))
                    .clickable { onClearClick() },
                contentDescription = stringResource(R.string.clear_chat),
            )
        }
    }
}

@Preview
@Composable
private fun ScreenHeaderPreview() {
    VoiceAssistantTheme {
        ScreenHeader(isDarkTheme = false, onThemeChange = {}, onClearClick = {})
    }
}