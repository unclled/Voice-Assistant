package com.project.voiceassistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.voiceassistant.R
import com.project.voiceassistant.model.Message
import com.project.voiceassistant.ui.components.button.CustomButton
import com.project.voiceassistant.ui.components.header.ScreenHeader
import com.project.voiceassistant.ui.components.input.TextFieldContainer
import com.project.voiceassistant.ui.components.input.TextFieldInput
import com.project.voiceassistant.ui.components.message.AssistantResponse
import com.project.voiceassistant.ui.components.message.UserResponse
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkTheme = uiState.isDarkTheme

    VoiceAssistantTheme(darkTheme = isDarkTheme) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VoiceAssistantTheme.colors.backgroundColor)
        ) {
            ScreenHeader(
                isDarkTheme = isDarkTheme,
                onThemeChange = viewModel::updateTheme,
                onClearClick = viewModel::clearChat
            )

            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_8)
                    )
                    .padding(
                        top = dimensionResource(R.dimen.padding_4),
                        bottom = dimensionResource(R.dimen.padding_20)
                    )
            ) {
                MessageList(
                    messages = uiState.messages,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextFieldContainer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = dimensionResource(R.dimen.padding_8)),
                    ) {
                        TextFieldInput(
                            inputText = uiState.inputText,
                            placeholderText = stringResource(R.string.ask_question),
                            onValueChange = viewModel::updateInputText,
                            keyboardController = keyboardController
                        )
                    }

                    CustomButton(
                        text = stringResource(R.string.send),
                        onButtonClick = viewModel::sendMessage
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageList(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_4)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8))
    ) {
        items(messages) { message ->
            if (message.isSend) {
                UserResponse(message = message.text, timestamp = message.date)
            } else {
                AssistantResponse(message = message.text, timestamp = message.date)
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    VoiceAssistantTheme {
        MainScreen()
    }
}