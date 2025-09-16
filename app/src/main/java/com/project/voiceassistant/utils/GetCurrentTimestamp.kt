package com.project.voiceassistant.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentTimestamp(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
}