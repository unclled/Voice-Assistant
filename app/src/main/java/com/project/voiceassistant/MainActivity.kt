package com.project.voiceassistant

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.project.voiceassistant.ui.screens.MainScreen
import com.project.voiceassistant.ui.theme.VoiceAssistantTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoiceAssistantTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.Transparent
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@HiltAndroidApp
class VoiceAssistantApp : Application()