package com.project.voiceassistant.repository.db

import com.project.voiceassistant.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getAllMessages(): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
    suspend fun clearAllMessages()
}