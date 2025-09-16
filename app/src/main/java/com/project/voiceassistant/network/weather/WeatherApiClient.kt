package com.project.voiceassistant.network.weather

import android.util.Log
import com.project.voiceassistant.BuildConfig
import com.project.voiceassistant.di.WeatherClient
import com.project.voiceassistant.model.weather.Forecast
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class WeatherApiClient @Inject constructor(
    @WeatherClient private val httpClient: HttpClient
) {
    private val openWeatherApiKey = BuildConfig.OPENWEATHERMAP_API_KEY

    suspend fun getOpenWeatherMapData(city: String): Forecast {
        Log.d("VoiceAssistantDebug", "1: Внутри WeatherApiClient. Запрашиваю погоду для: $city")
        return httpClient.get("data/2.5/weather") {
            parameter("q", city)
            parameter("appid", openWeatherApiKey)
            parameter("units", "metric")
        }.body<Forecast>()
    }
}