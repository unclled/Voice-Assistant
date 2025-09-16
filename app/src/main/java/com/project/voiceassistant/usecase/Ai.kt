package com.project.voiceassistant.usecase

import android.content.Context
import android.util.Log
import com.project.voiceassistant.R
import com.project.voiceassistant.repository.geo.GeoRepository
import com.project.voiceassistant.repository.weather.WeatherRepository
import com.project.voiceassistant.utils.NumberToWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Ai @Inject constructor(
    private val context: Context,
    private val weatherRepository: WeatherRepository,
    private val geoRepository: GeoRepository
) {
    private val weatherPattern = Pattern.compile(
            "(?:погода|градусов)(?:\\sв)?\\s([\\p{L}\\s-]+)", Pattern.CASE_INSENSITIVE)
    private val geoSearchPattern = Pattern.compile("(?<=найди\\s)(.+)", Pattern.CASE_INSENSITIVE)
    private val numberToWordsPattern =
        Pattern.compile("(?<=число прописью\\s)(\\d+)", Pattern.CASE_INSENSITIVE)

    val currentDate: LocalDate = LocalDate.now()
    val currentTime: LocalTime = LocalTime.now()

    suspend fun getAnswer(text: String): String {
        val lowerText = text.lowercase()

        val weatherMatcher = weatherPattern.matcher(text)
        val geoSearchMatcher = geoSearchPattern.matcher(text)
        val numberToWordsMatcher = numberToWordsPattern.matcher(text)

        val weatherCityName = if (weatherMatcher.find()) weatherMatcher.group(1)?.trim() else ""
        val geoQuery = if (geoSearchMatcher.find()) geoSearchMatcher.group(1)?.trim() else ""
        val numberToConvert = if (numberToWordsMatcher.find()) numberToWordsMatcher.group(1)?.trim() else null

        return when {
            weatherCityName!!.isNotBlank() -> getWeatherInfo(weatherCityName)
            geoQuery!!.isNotBlank() -> getGeoInfo(geoQuery)
            numberToConvert != null -> convertNumberToWords(numberToConvert)

            lowerText.contains("привет") || lowerText.contains("здравствуй") ->
                context.getString(R.string.hello_assistant)

            lowerText.contains("как дела") || lowerText.contains("как ты") ||
                    lowerText.contains("настроение") -> context.getString(R.string.how_are_you)

            lowerText.contains("чем занимаешься") || lowerText.contains("что делаешь") ||
                    lowerText.contains("работаешь") -> context.getString(R.string.what_are_you_doing)

            lowerText.contains("сегодня день") ->
                context.getString(R.string.today_date, currentDate.toString())

            lowerText.contains("который час") || lowerText.contains("какой час") ||
                    lowerText.contains("сколько времени") ->
                context.getString(R.string.current_time, currentTime.toString())

            lowerText.contains("день недели") ->
                context.getString(R.string.day_of_week, currentDate.dayOfWeek.toString())

            lowerText.contains("дней до") -> {
                val targetDate = extractDateFromText(lowerText)
                if (targetDate != null) {
                    getDaysBefore(targetDate)
                } else {
                    context.getString(R.string.invalid_date_format)
                }
            }

            else -> context.getString(R.string.not_understood)
        }
    }

    private suspend fun getWeatherInfo(cityName: String): String {
        return if (cityName.isNotBlank()) {
            Log.d("VoiceAssistantDebug", "4: Внутри Ai.getWeatherInfo. Вызываю репозиторий.")
            withContext(Dispatchers.IO) {
                weatherRepository.getWeatherStringForCity(cityName)
            }
        } else {
            context.getString(R.string.incorrect_city)
        }
    }

    private suspend fun getGeoInfo(query: String): String {
        return if (query.isNotBlank()) {
            withContext(Dispatchers.IO) {
                geoRepository.getGeoInfoString(query)
            }
        } else {
            "Пожалуйста, укажите, что нужно найти."
        }
    }

    private fun convertNumberToWords(numberStr: String?): String {
        return if (numberStr != null) {
            try {
                NumberToWords.convert(numberStr.toLong())
            } catch (_: NumberFormatException) {
                "Неверный формат числа."
            }
        } else {
            "Пожалуйста, укажите число для преобразования."
        }
    }

    private fun extractDateFromText(text: String): LocalDate? {
        val pattern = Regex("""(\d{1,2})\.(\d{1,2})\.(\d{4})""")
        val match = pattern.find(text)

        return try {
            match?.let {
                val day = it.groupValues[1].toInt()
                val month = it.groupValues[2].toInt()
                val year = it.groupValues[3].toInt()
                LocalDate.of(year, month, day)
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun getDaysBefore(targetDate: LocalDate): String {
        val daysDifference = ChronoUnit.DAYS.between(currentDate, targetDate)

        return when {
            daysDifference > 0 -> context.getString(
                R.string.days_until,
                targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                daysDifference
            )

            daysDifference < 0 -> context.getString(
                R.string.date_passed,
                targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )

            else -> context.getString(
                R.string.same_date,
                targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )
        }
    }
}