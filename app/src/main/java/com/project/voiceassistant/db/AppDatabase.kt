package com.project.voiceassistant.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.voiceassistant.db.dao.MessageDao
import com.project.voiceassistant.db.entity.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}