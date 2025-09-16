package com.project.voiceassistant.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.voiceassistant.model.Message
import com.project.voiceassistant.repository.db.MessageRepository
import com.project.voiceassistant.usecase.Ai
import com.project.voiceassistant.utils.TextToSpeechService
import com.project.voiceassistant.utils.ThemeManager
import com.project.voiceassistant.utils.getCurrentTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ai: Ai,
    private val ttsService: TextToSpeechService,
    private val themeManager: ThemeManager,
    private val messageRepository: MessageRepository
) : ViewModel() {
    private val _inputText = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _themeFlow: StateFlow<Boolean> = themeManager.themeFlow
    private val _messagesFlow: Flow<List<Message>> = messageRepository.getAllMessages()

    val uiState: StateFlow<MainUiState> = combine(
        _inputText,
        _messagesFlow,
        _isLoading,
        _themeFlow
    ) { text, messages, loading, isDarkTheme->
        MainUiState(
            inputText = text,
            messages = messages,
            isLoading = loading,
            isDarkTheme = isDarkTheme
        )
    }.stateIn(
        scope = viewModelScope,
        // Поток будет активен, пока на него есть хотя бы один подписчик (экран)
        // и еще 5 секунд после того, как последний подписчик отписался
        // Предотвращает перезапуск потока при смене конфигурации
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState()
    )

    init {
        ttsService.initialize()
    }

    fun sendMessage() {
        val currentText = _inputText.value
        if (currentText.isBlank()) {
            return
        }

        val userMessage = Message(text = currentText, date = getCurrentTimestamp(), isSend = true)
        viewModelScope.launch {
            messageRepository.saveMessage(userMessage)
        }
        _inputText.value = ""

        viewModelScope.launch {
            _isLoading.value = true
            val aiResponse = ai.getAnswer(currentText)
            val assistantMessage = Message(text = aiResponse, date = getCurrentTimestamp() , isSend = false)
            messageRepository.saveMessage(assistantMessage)
            ttsService.speak(aiResponse)
            _isLoading.value = false
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            messageRepository.clearAllMessages()
        }
    }

    fun updateTheme(isDark: Boolean) {
        themeManager.saveTheme(isDark)
    }

    fun updateInputText(text: String) {
        _inputText.value = text
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.shutdown()
    }
}

