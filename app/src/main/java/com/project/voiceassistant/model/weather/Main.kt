package com.project.voiceassistant.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    var temp: Double? = null
)
