package com.project.voiceassistant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.voiceassistant.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(messageEntity: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY id ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("DELETE FROM messages")
    suspend fun clearAllMessages()
}