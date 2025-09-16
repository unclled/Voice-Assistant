package com.project.voiceassistant.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    var description: String? = null
)