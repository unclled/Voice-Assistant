package com.project.voiceassistant.ui.screens

import com.project.voiceassistant.model.Message

data class MainUiState(
    val inputText: String = "",
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false
)
