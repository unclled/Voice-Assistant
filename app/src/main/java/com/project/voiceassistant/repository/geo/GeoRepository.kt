package com.project.voiceassistant.repository.geo

interface GeoRepository {
    suspend fun getGeoInfoString(query: String): String
}