package com.project.voiceassistant.network.geo

import com.project.voiceassistant.model.geo.GeoSearchResponse
import javax.inject.Inject

class GeoApiServiceImpl @Inject constructor(
    private val geoApiClient: GeoApiClient
) : GeoApiService {
    override suspend fun searchGeo(query: String): Result<GeoSearchResponse> {
        return runCatching {
            geoApiClient.search(query)
        }
    }
}