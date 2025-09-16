package com.project.voiceassistant.ui.components.message

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.components.message.common.MessageCard
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun UserResponse(
    message: String,
    timestamp: String
) {
    MessageCard(
        name = stringResource(R.string.user),
        timestamp = timestamp,
        horizontalAlignment = Alignment.End,
        message = message,
        isUser = true
    )
}

@Preview
@Composable
fun UserResponsePreview() {
    VoiceAssistantTheme {
        UserResponse(message = "Привет! Я Данил, а ты?", timestamp = "20:15")
    }
}