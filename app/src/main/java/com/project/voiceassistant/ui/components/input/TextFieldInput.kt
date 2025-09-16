package com.project.voiceassistant.ui.components.input

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import com.project.voiceassistant.R
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme

@Composable
fun TextFieldInput(
    inputText: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_20))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = inputText,
            onValueChange = onValueChange,
            textStyle = VoiceAssistantTheme.typography.bodyMedium.copy(
                color = VoiceAssistantTheme.colors.placeholderContentColor
            ),
            singleLine = true,
            cursorBrush = SolidColor(VoiceAssistantTheme.colors.placeholderContentColor),
            decorationBox = { innerTextField ->
                if (inputText.isEmpty()) {
                    Text(
                        text = placeholderText,
                        style = VoiceAssistantTheme.typography.bodyMedium,
                        color = VoiceAssistantTheme.colors.placeholderContentColor,
                        maxLines = 1
                    )
                }
                innerTextField()
            },
            maxLines = 1,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}