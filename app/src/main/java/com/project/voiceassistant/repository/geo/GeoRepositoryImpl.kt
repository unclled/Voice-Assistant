package com.project.voiceassistant.repository.geo

import android.content.Context
import com.project.voiceassistant.R
import com.project.voiceassistant.network.geo.GeoApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoApiService: GeoApiService,
    @ApplicationContext private val context: Context
) : GeoRepository {
    override suspend fun getGeoInfoString(query: String): String {
        val result = geoApiService.searchGeo(query)

        if (result.isFailure) {
            return context.getString(R.string.geo_search_network_error, query)
        }

        val response = result.getOrThrow()

        // 1. Извлекаем ПОЛНЫЕ ИМЕНА только из поля "city"
        val fullNames = response.city?.values?.mapNotNull { it.fullName }

        // 2. Проверяем, нашли ли мы что-нибудь в этом поле
        if (fullNames.isNullOrEmpty()) {
            return context.getString(R.string.geo_search_not_found, query)
        }

        // 3. Форматируем результат в чистый список без лишних добавлений
        val header = context.getString(R.string.geo_search_results_header, query)

        // Берем до 5 первых результатов и объединяем их в одну строку
        val locationsListString = fullNames.take(5).joinToString(separator = "\n")

        return "$header\n$locationsListString"
    }
}