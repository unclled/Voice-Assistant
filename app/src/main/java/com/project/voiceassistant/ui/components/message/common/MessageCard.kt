package com.project.voiceassistant.ui.components.message.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun MessageCard(
    name: String,
    timestamp: String,
    message: String,
    isUser: Boolean = true,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_8))
            .fillMaxWidth(),
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = name,
            style = VoiceAssistantTheme.typography.titleMedium,
            color = VoiceAssistantTheme.colors.primaryContentColor,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_4))
        )

        MessageRow(message = message, isUser = isUser)

        Text(
            text = timestamp,
            style = VoiceAssistantTheme.typography.bodySmall,
            color = VoiceAssistantTheme.colors.secondaryContentColor,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_4))
                .padding(horizontal = dimensionResource(R.dimen.padding_60))
        )
    }
}