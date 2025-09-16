package com.project.voiceassistant.repository.weather

import android.content.Context
import android.util.Log
import com.project.voiceassistant.R
import com.project.voiceassistant.network.weather.WeatherApiService
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val context: Context
) : WeatherRepository {
    override suspend fun getWeatherStringForCity(city: String): String {
        Log.d("VoiceAssistantDebug", "3: Внутри WeatherRepository. Вызываю apiService.")
        val forecastResult = apiService.getWeatherForCity(city)

        return if (forecastResult.isSuccess) {
            val forecast = forecastResult.getOrNull()
            if (forecast != null && forecast.main != null && forecast.main!!.temp != null && forecast.weather.isNotEmpty()) {
                val temp = forecast.main!!.temp
                val tempAsInt = temp!!.roundToInt()
                val degreesText = context.resources.getQuantityString(R.plurals.degree_plural, tempAsInt, tempAsInt)
                val description = forecast.weather.firstOrNull()?.description ?: context.getString(R.string.no_data)
                Log.d("VoiceAssistantDebug", "3.1: WeatherRepository возвращает строку")
                context.getString(R.string.weather_in, city, temp) + " " + degreesText + ", " + description
            } else {
                context.getString(R.string.can_not_get_data, city)
            }
        } else {
            context.getString(R.string.error_occurred, city)
        }
    }
}