package com.project.voiceassistant.ui.components.message.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun MessageRow(
    message: String,
    isUser: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isUser) {
            MessageText(message = message, modifier = Modifier.weight(1f, fill = false))
            ProfilePicture(isUser = isUser)
        } else {
            ProfilePicture(isUser = isUser)
            MessageText(message = message, modifier = Modifier.weight(1f, fill = false))
        }
    }
}

@Composable
private fun MessageText(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        style = VoiceAssistantTheme.typography.bodyMedium,
        color = VoiceAssistantTheme.colors.primaryContentColor,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_8))
    )
}

@Composable
private fun ProfilePicture(
    isUser: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(dimensionResource(R.dimen.icon_card_size))
            .border(
                width = dimensionResource(R.dimen.border_width_1),
                color = VoiceAssistantTheme.colors.primaryContentColor,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_12))
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (isUser) {
                painterResource(id = R.drawable.user)
            } else {
                painterResource(id = R.drawable.bot)
            },
            contentDescription = stringResource(R.string.user_icon),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_36))
        )
    }
}

@Preview
@Composable
fun MessageRowPreview() {
    VoiceAssistantTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8))
        ) {
            MessageRow(
                message = "Привет! Я Данил, а ты?",
                isUser = true
            )
            MessageRow(
                message = "Привет! Я твой личный ассистент!",
                isUser = false
            )
        }
    }
}