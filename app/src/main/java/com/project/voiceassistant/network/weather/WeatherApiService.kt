package com.project.voiceassistant.network.weather

import com.project.voiceassistant.model.weather.Forecast

interface WeatherApiService {
    suspend fun getWeatherForCity(prompt: String): Result<Forecast>
}