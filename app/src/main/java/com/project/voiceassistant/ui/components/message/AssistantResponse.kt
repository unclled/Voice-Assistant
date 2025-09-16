package com.project.voiceassistant.ui.components.message

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.components.message.common.MessageCard
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun AssistantResponse(
    message: String,
    timestamp: String
) {
    MessageCard(
        name = stringResource(R.string.assistant),
        timestamp = timestamp,
        horizontalAlignment = Alignment.Start,
        message = message,
        isUser = false
    )
}

@Preview
@Composable
fun AssistantResponsePreview() {
    VoiceAssistantTheme {
        AssistantResponse(message = "Привет!", timestamp = "20:15")
    }
}