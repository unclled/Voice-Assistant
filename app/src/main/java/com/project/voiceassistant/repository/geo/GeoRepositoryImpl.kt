package com.project.voiceassistant.repository.geo

import android.util.Log
import com.project.voiceassistant.network.geo.GeoApiService
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoApiService: GeoApiService
) : GeoRepository {
    override suspend fun getGeoInfoString(query: String): String {
        val result = geoApiService.searchGeo(query)

        if (result.isFailure) {
            return "Не удалось выполнить поиск по запросу '$query' (сетевая ошибка)."
        }

        val response = result.getOrThrow()
        if (response.error != null) {
            return "Ошибка API: ${response.error}"
        }

        val builder = StringBuilder()
        builder.append("Вот что удалось найти по запросу '$query':\n")

        response.city?.values?.firstOrNull()?.let {
            builder.append("Город: ${it.name} (${it.country})\n")
        }
        response.area?.firstOrNull()?.let {
            builder.append("Область: ${it.name}\n")
        }
        response.rajon?.firstOrNull()?.let {
            builder.append("Район: ${it.name}\n")
        }
        response.country?.firstOrNull()?.let {
            builder.append("Страна: ${it.name}\n")
        }

        if (builder.length <= "Вот что удалось найти по запросу '$query':\n".length) {
            return "По запросу '$query' ничего не найдено."
        }

        return builder.toString().trim()
    }
}