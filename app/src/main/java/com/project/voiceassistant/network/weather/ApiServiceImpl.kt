package com.project.voiceassistant.network.weather

import android.util.Log
import com.project.voiceassistant.model.weather.Forecast
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val weatherClient: WeatherApiClient
) : WeatherApiService {
    override suspend fun getWeatherForCity(city: String): Result<Forecast> {
        Log.d("VoiceAssistantDebug", "2: Внутри ApiServiceImpl. Вызываю weatherClient.")
        return runCatching {
            weatherClient.getOpenWeatherMapData(city)
        }.onSuccess {
            Log.d("VoiceAssistantDebug", "2.1: УСПЕХ в ApiServiceImpl. Данные получены.")
        }.onFailure {
            Log.e("VoiceAssistantDebug", "2.2: ОШИБКА в ApiServiceImpl.", it)
        }
    }
}