package com.project.voiceassistant.ui.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun CustomButton(
    text: String,
    onButtonClick: () -> Unit
) {
    Button(
        onClick = onButtonClick,
        modifier = Modifier
            .height(dimensionResource(R.dimen.button_height))
            .width(dimensionResource(R.dimen.button_width)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_12)),
        colors = ButtonDefaults.buttonColors(
            containerColor = VoiceAssistantTheme.colors.accentColor.copy(alpha = 0.75f),
            contentColor = VoiceAssistantTheme.colors.primaryContentColor,
        ),
        elevation = null,
    ) {
        Text(
            text = text,
            style = VoiceAssistantTheme.typography.bodyMedium.copy(
                color = VoiceAssistantTheme.colors.primaryContentColor
            )
        )
    }
}