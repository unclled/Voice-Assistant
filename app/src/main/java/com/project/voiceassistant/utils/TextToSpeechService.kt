package com.project.voiceassistant.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private lateinit var textToSpeech: TextToSpeech
    private var isInitialized = false

    fun initialize() {
        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            isInitialized = status != TextToSpeech.ERROR
            if (isInitialized) {
                textToSpeech.language = Locale.getDefault()
                Locale.setDefault(Locale("ru"))
            }
        })
    }

    fun speak(text: String) {
        if (isInitialized) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun shutdown() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
}