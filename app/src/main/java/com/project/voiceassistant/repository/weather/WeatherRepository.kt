package com.project.voiceassistant.repository.weather

interface WeatherRepository {
    suspend fun getWeatherStringForCity(city: String): String
}