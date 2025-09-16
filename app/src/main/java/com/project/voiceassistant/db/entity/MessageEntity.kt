package com.project.voiceassistant.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "text_content")
    val text: String,
    @ColumnInfo(name = "message_date")
    val date: String,
    @ColumnInfo(name = "is_user_message")
    val isSend: Boolean
)
