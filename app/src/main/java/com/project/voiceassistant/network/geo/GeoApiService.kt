package com.project.voiceassistant.network.geo

import com.project.voiceassistant.model.geo.GeoSearchResponse

interface GeoApiService {
    suspend fun searchGeo(query: String): Result<GeoSearchResponse>
}