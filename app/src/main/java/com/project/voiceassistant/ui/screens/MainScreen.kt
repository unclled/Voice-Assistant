package com.project.voiceassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ScreenHeader(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = viewModel::updateTheme,
                    onClearClick = viewModel::clearChat
                )
            },
            bottomBar = {
                InputBar(
                    inputText = uiState.inputText,
                    onValueChange = viewModel::updateInputText,
                    onSendClick = viewModel::sendMessage,
                    keyboardController = keyboardController,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_16))
                )
            },
            containerColor = VoiceAssistantTheme.colors.backgroundColor
        ) { innerPadding ->
            MessageList(
                messages = uiState.messages,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = dimensionResource(R.dimen.padding_8))
            )
        }
    }
}
@Composable
private fun InputBar(
    modifier: Modifier = Modifier,
    inputText: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    Surface(
        color = VoiceAssistantTheme.colors.backgroundColor,
        tonalElevation = dimensionResource(R.dimen.padding_4)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_8)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldContainer(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensionResource(R.dimen.padding_8)),
            ) {
                TextFieldInput(
                    inputText = inputText,
                    placeholderText = stringResource(R.string.ask_question),
                    onValueChange = onValueChange,
                    keyboardController = keyboardController
                )
            }

            CustomButton(
                text = stringResource(R.string.send),
                onButtonClick = onSendClick
            )
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