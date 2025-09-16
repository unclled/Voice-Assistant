package com.project.voiceassistant.model

import com.project.voiceassistant.utils.getCurrentTimestamp

data class Message(
    val text: String,
    val date: String = getCurrentTimestamp(),
    val isSend: Boolean
)