package com.project.voiceassistant.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    var main: Main? = null,
    var weather: ArrayList<Weather?> = ArrayList()
)
