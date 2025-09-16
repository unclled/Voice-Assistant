package com.project.voiceassistant.repository.db

import com.project.voiceassistant.db.dao.MessageDao
import com.project.voiceassistant.db.entity.MessageEntity
import com.project.voiceassistant.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageRepository {
    override fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages().map { listOfMessages ->
            listOfMessages.map { message ->
                Message(
                    text = message.text,
                    date = message.date,
                    isSend = message.isSend
                )
            }
        }
    }

    override suspend fun saveMessage(message: Message) {
        messageDao.insertMessage(
            MessageEntity(
                text = message.text,
                date = message.date,
                isSend = message.isSend
            )
        )
    }

    override suspend fun clearAllMessages() {
        messageDao.clearAllMessages()
    }
}